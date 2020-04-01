package dev.chu.memo.z_test.rxjava_200321

import android.annotation.SuppressLint
import dev.chu.memo.entity.GithubRepo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

object ExampleOperator {

    fun kotlinExample() {
        val repo = GithubRepo()
        // 자기 자신을 넘기고 뭔가를 바꿈
        repo.let {
            // 자기 자신을 인자로 받고, 리턴 값이 나옴
            it.name
        }
        repo.run {
            // 자기 자신을 블럭으로 넘긴다.
            name
        }
        // let, run 은 넘기는 차이

        // also, apply 는 return 차이 (자기 자신을 리턴)
        repo.also {
            it.name
        }
        repo.apply {
            name = "3"
        }

        with(repo) {

        }
    }

    fun collections() {
        val list = listOf(1, 2, 3)
        list.forEach { println("it = $it") }
        list.map { it+2 }
        list.forEach { println("it = $it") }
    }

    fun observable() {
        Single.just(true)
            .map {
                false
            }
    }

    @SuppressLint("CheckResult")
    fun example() {
        val list = listOf(1, 2, 3)
//        list.map { it+2 }
        list.flatMap {
//            it+2 // error

            // 원소가 하나씩 들어온다.
            // 1을 map하면 무언가로 변한다.
            // 1을 flatMap하면 무언가의 콜랙션으로 변한다.

            // flatMap은 각 원소 하나당 리스트로 내부적으로 변한다.
            // list(1, 2, 3) -> flatMap으로 하면 -> list(list(1), list(2), list(3)) 인 형태가 되므로 list(it+2)로 해야 오류가 안난다.
            listOf(it+2)
        }

        // true인 아이템이 false로 변함
        Single.just(true)
            .map {
                false
            }

        // flatMap은 true인 옵저버블이 false인 옵저버블로 변한다.
        Single.just(true)
            .flatMap {
                Single.just(false)
            }

        Observable.just(1, 2 ,3)
            .debounce(1, TimeUnit.SECONDS)

        Observable.just(1, 2 ,3)
            .throttleFirst(500, TimeUnit.MILLISECONDS)

        Single.zip(
            Single.just(1),     // api1
            Single.just(2),     // api2
            BiFunction<Int, Int, Int> { t1, t2 ->
                t1 + t2
            }
        ).subscribe()
    }
}