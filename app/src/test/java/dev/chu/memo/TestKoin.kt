package dev.chu.memo

import dev.chu.memo.di_koin.myDiModule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

// Just tag your class with KoinTest to unlick your testing power
class TestKoin: KoinTest {

//    // lazy inject BusinessService into property
//    private val service: BusinessService by inject()
//    private val myPresenter: MyPresenter by inject() // 이 부분을 통해서 의존성 주입이 일어납니다.
//    private fun testKoin() {
//        println("코인 결과 : ${service.sayHello()}")
//        println("코인 결과 : ${myPresenter.sayHello()}")
//    }

    @Test
    fun unitTestToKoin() {
        // You can start your Koin configuration
        startKoin { modules(myDiModule) }

//        println("service = ${service.sayHello()}")
//        val service : BusinessService = get()
//        println("service = ${service.sayHello()}")
//
//        testKoin()

        // Don't forget to close it at the end
        stopKoin()
    }
}