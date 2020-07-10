package dev.chu.memo.data.remote

import com.google.gson.JsonElement
import dev.chu.memo.data.request.CreateIssueRequest
import dev.chu.memo.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // region corona
    // 약국, 우체국, 농협 등의 마스크 판매처 정보 제공 (마스크 재고 관련 정보는 제공하지 않음)
    // page : 페이지 번호
    // perPage : 한 페이지당 출력할 판매처 수[default: 500, min:500, max:5000]
    @GET("corona19-masks/v1/stores/json")
    fun getStores(@Query("page") page: Int, @Query("perPage") perPage: Int) : Single<StoreRes>

    // 마스크 재고 상태 등의 판매 정보 제공(판매처 관련 정보는 제공하지 않음)
    @GET("corona19-masks/v1/sales/json")
    fun getSales(@Query("page") page: Int, @Query("perPage") perPage: Int) : Single<SaleRes>

    // 중심 좌표(위/경도)를 기준으로 반경(미터단위) 안에 존재하는 판매처 및 재고 상태 등의 판매 정보 제공
    // lat : 위도(wgs84 좌표계) / 최소:33.0, 최대:43.0
    // lng : 경도(wgs84 표준) / 최소:124.0, 최대:132.0
    // m : 반경(미터) / 최대 5000(5km)까지 조회 가능
    @GET("corona19-masks/v1/storesByGeo/json")
    fun getStoresByGeo(@Query("lat") lat: Double, @Query("lng") lng: Double, @Query("m") m: Int) : Single<StoresByGeoRes>

    // 주소를 기준으로 해당 구 또는 동내에 존재하는 판매처 및 재고 상태 등의 판매 정보 제공.
    // 예- '서울특별시 강남구' or '서울특별시 강남구 논현동'
    // ('서울특별시' 와 같이 '시'단위만 입력하는 것은 불가능합니다.)
    @GET("corona19-masks/v1/storesByAddr/json")
    fun getStoresByAddr(@Query("address") address: String): Single<StoresByAddrRes>
    // endregion

    // region github
    @GET("search/repositories")
    fun searchRepos(
        @Query("q") search: String
    ): Single<JsonElement>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @PUT("user/starred/{owner}/{repo}")
    fun star(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @DELETE("user/starred/{owner}/{repo}")
    fun unstar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @GET("/repos/{owner}/{repo}/issues")
    fun issues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<Issue>>

    @POST("/repos/{owner}/{repo}/issues")
    fun createIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body request: CreateIssueRequest
    ): Single<Issue>

    @GET("/users")
    fun getUserAsyncRx() : Single<List<User>>

    @GET("users")
    suspend fun getUserAsync() : Response<List<User>>

    @GET("/search/repositories?q=stars:>1&sort=stars&per_page=100")
    suspend fun getPopularRepos(): Repository

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<RepoSearchResponse>
    // endregion
}