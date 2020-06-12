package dev.chu.memo.di_koin

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.chu.memo.BuildConfig
import dev.chu.memo.common.Const
import dev.chu.memo.data.local.FavTvShowsDatabase
import dev.chu.memo.data.local.GithubDatabase
import dev.chu.memo.data.local.MemoDatabase
import dev.chu.memo.data.remote.ApiService
import dev.chu.memo.data.repository.*
import dev.chu.memo.ui.fav_tv_shows.FavTvShowsAdapter
import dev.chu.memo.ui.fav_tv_shows.FavTvShowsViewModel
import dev.chu.memo.ui.map.CoronaViewModel
import dev.chu.memo.ui.memo.MemoViewModel
import dev.chu.memo.ui.memo_add.ImageModifyAdapter
import dev.chu.memo.ui.memo_read.ImageShowAdapter
import dev.chu.memo.ui.merge_adapter.ReposAdapter
import dev.chu.memo.ui.merge_adapter.SearchRepositoriesViewModel
import dev.chu.memo.ui.mvi.MviViewModel
import dev.chu.memo.ui.recycler_multi_viewtype.ui.RecyclerViewViewModel
import dev.chu.memo.ui.recycler_multi_viewtype.etc.ResourceProvider
import dev.chu.memo.ui.rv_coroutine.UserAdapter
import dev.chu.memo.ui.rv_coroutine.UserViewModel
import dev.chu.memo.ui.rx_activity.repo.GithubRepoViewModel
import dev.chu.memo.ui.rx_activity.repo.IssuesAdapter
import dev.chu.memo.ui.rx_activity.repos.GithubReposAdapter
import dev.chu.memo.ui.rx_activity.repos.GithubReposViewModel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin은 크게 아래 단계를 통해서 구현을 하게 됩니다.
 * 1. 모듈 생성하기 (Koin DSL)
 * 2. Android Application Class 에서 startKoin()으로 실행하기
 * 3. 의존성 주입
 *
 * module 이란? 의존성 주입을 하는 실제 코드, 혹은 의존성 주입을 위한 설계도
 * DSL 이란? Domain Specific Language 로, 도메인 특화 언어, 즉, "특정 도메인을 적용하는데 특화된 언어"를 말합니다.
 *
 * Koin DSL 은 아래와 같다.
 * applicationContext : Koin 모듈 생성
 * factory : 매번 inject 할때마다 인스턴스 생성 -> 호출할 때마다 매번 객체를 생생해 사용
 * single : 싱글톤 생성 -> 한번 생성 후 메모리에서 계속 가지고 있음
 * bind : 종속시킬 class 나 interface 를 주입
 * get : 컴포넌트내에서 알맞은 의존성을 주입함
 */


private const val CORONA = "CORONA"
private const val GITHUB = "GITHUB"
private const val GITHUB_RX = "GITHUB_RX"

val networkModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10*1024*1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
            })        // Retrofit 에서 통신 과정의 로그를 확인하기 위함. 로그의 level 을 지정
            connectTimeout(10000L, TimeUnit.SECONDS)       // 요청을 시작한 후 서버와의 TCP handshake가 완료되기까지 지속되는 시간. 즉, Retrofit이 설정된 연결 시간 제한 내에서 서버에 연결할 수 없는 경우 해당 요청을 실패한 것으로 계산.
            readTimeout(10000L, TimeUnit.SECONDS)          // 읽기 시간 초과는 연결이 설정되면 모든 바이트가 전송되는 속도를 감시. 서버로부터의 응답까지의 시간이 읽기 시간 초과보다 크면 요청이 실패로 계산.
            writeTimeout(10000L, TimeUnit.SECONDS)         // 읽기 타임 아웃의 반대 방향, 얼마나 빨리 서버에 바이트를 보낼 수 있는지 확인.
            cache(cache)
        }

        return okHttpClientBuilder.build()
    }

    fun provideGson() : Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    fun provideRetrofitGitHub(factory: Gson, client: OkHttpClient, url: String): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(factory))

    single { provideCache(androidApplication()) }
    single { provideOkHttpClient(get()) }
    single { provideGson() }

    single(named(CORONA)) {
        provideRetrofitGitHub(get(), get(), Const.URL_CORONA)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    single(named(GITHUB)) {
        provideRetrofitGitHub(get(), get(), Const.URL_GITHUB)
            .build()
    }
    single(named(GITHUB_RX)) {
        Retrofit.Builder()
            .baseUrl(Const.URL_GITHUB)
            .client(OkHttpClient.Builder().apply {
                addInterceptor( Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .header("Authorization", "token ${Const.TOKEN}")
                    chain.proceed(requestBuilder.build())
                })
                addInterceptor(HttpLoggingInterceptor().apply {
                    if(BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
                })
            }.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}

val apiModule = module {
    fun provideService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    single(named(CORONA)) { provideService(get(named(CORONA))) }
    single(named(GITHUB)) { provideService(get(named(GITHUB))) }
    single(named(GITHUB_RX)) { provideService(get(named(GITHUB_RX))) }
}

val roomModule = module {
    factory { MemoDatabase.getInstance(androidApplication()).getMemoDao() }
    factory { GithubDatabase.get().githubDao() }
    factory { FavTvShowsDatabase.getInstance(androidApplication()).getFavTvShowsDao() }

}
val repositoryModule = module {
    factory { RoomRepository(get()) }
    factory { StoreRepository(get(named(CORONA))) }
    factory { GithubRepository(get(named(GITHUB_RX)), get()) }
    factory { UsersRepository(get(named(GITHUB))) }
    factory { MergeRepository(get(named(GITHUB))) }
}

val viewModelModule = module {
    viewModel { MemoViewModel(get()) }
    viewModel { CoronaViewModel(get()) }
    viewModel { UserViewModel(get(named(GITHUB))) }
    viewModel { MviViewModel(androidApplication(), get()) }
    viewModel { SearchRepositoriesViewModel(get()) }
    viewModel { FavTvShowsViewModel(get()) }
    factory { GithubReposViewModel(get()) }
    factory { GithubRepoViewModel(get()) }
}

val adapterModule = module {
    factory { ImageShowAdapter() }
    factory { ImageModifyAdapter(mutableListOf()) }
    factory { GithubReposAdapter() }
    factory { IssuesAdapter() }
    factory { UserAdapter()}
    factory { ReposAdapter() }
    factory { FavTvShowsAdapter(mutableListOf()) }
}



val recyclerViewActivityModule = module {
    viewModel { RecyclerViewViewModel(get()) }
    single { ResourceProvider(androidApplication()) }
}



val myDiModule = listOf(networkModule, apiModule, roomModule, repositoryModule, viewModelModule, adapterModule, recyclerViewActivityModule)