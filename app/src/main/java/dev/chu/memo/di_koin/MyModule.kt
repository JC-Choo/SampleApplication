package dev.chu.memo.di_koin

import dev.chu.memo.view_model.RoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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
 * factory : 매번 inject 할때마다 인스턴스 생성
 * single : 싱글톤 생성
 * bind : 종속시킬 class 나 interface 를 주입
 * get : 컴포넌트내에서 알맞은 의존성을 주입함
 */

// 여기서 Koin DSL을 이용하여 모듈을 만들어줍니다.
val appModule = module {
    single<Repository> { RepositoryImpl() }   // 싱글톤으로 생성
    factory { MyPresenter(get()) }   // 의존성 주입때마다 인스턴스 생성, get()을 이용하면 위에서 선언된 적절한 클래스가 주입됩니다.
}

// region github
// Given some classes
class Controller(private val service: BusinessService) {
    fun hello() = service.sayHello()
}
class BusinessService() {
    fun sayHello() = "Hello Koin $this"
}

// just declare it
val myModule = module {
    single { Controller(get()) }
    single { BusinessService() }
}
// endregion

val viewModelRoom = module {
    viewModel {
        RoomViewModel(get())
    }
}

val myDiModule = listOf(appModule, myModule, viewModelRoom)