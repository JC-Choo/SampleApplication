package dev.chu.memo.di_koin

// 생성자에서 repository의 인스턴스가 의존성 주입이 되는데, 모듈에서의 get()을 통해서 자동으로 주입이 됩니다.
class MyPresenter(private val repository: Repository) {
    fun sayHello() = "${repository.getMyData()} from $this"
}