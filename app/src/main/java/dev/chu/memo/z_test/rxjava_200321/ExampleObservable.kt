package dev.chu.memo.z_test.rxjava_200321

import android.annotation.SuppressLint
import android.util.Log
import dev.chu.memo.etc.extension.TAG
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
object ExampleObservable {

    fun subscribeSample() {
        Single.just(true)   // observable
            .subscribe(object : DisposableSingleObserver<Boolean>() {
                // stream 을 성공했을 때 발생! 왜냐면 onNext() 가 하나일 뿐이니까 바로 성공(onComplete())!
                override fun onSuccess(t: Boolean) {
                    // doSomething
                }

                // 에러 발생 시 발행!!
                override fun onError(e: Throwable) {

                }
            })

        // lambda 는 interface 하나일 경우만 가능!!! 위는 2개(onSuccess onError) 니까 안돼!!!
        Single.just(true)
            .subscribe ({ result ->

            }, { t ->

            })

        Observable.just(true)
            .subscribe(object : Observer<Boolean> {
                // 더 이상 이벤트 발행을 하지 않을거야! 일 때 발생!
                override fun onComplete() {
                    TODO("Not yet implemented")
                }

                // 필요 없음
                override fun onSubscribe(d: Disposable) {
                    TODO("Not yet implemented")
                }

                // onSuccess 랑 같은데, 이름만 명확하게 바뀐게 onSuccess!!! onNext는 Iterable 패턴에서 사용(hasNext())해 사용될 뿐!
                // stream을 성공할 때 발행!!
                override fun onNext(t: Boolean) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun exampleConnectable() {
//        val publishObservable = Observable.just(true)
//            .publish()
        val publishObservable = Observable.interval(1, TimeUnit.SECONDS)
            .publish()  // cold -> hot으로 바꿔준다!

        publishObservable.subscribe {
            Log.d(TAG, "Observer1 value : $it")
        }

        publishObservable.connect()

        publishObservable.subscribe {
            Log.d(TAG, "Observer2 value : $it")
        }

        Thread.sleep(2000)

        publishObservable.subscribe {
            Log.d(TAG, "Observer3 value : $it")
        }
    }

    fun exampleConnectable2() {
        val publishObservable = Observable.interval(1, TimeUnit.SECONDS)
            .publish().refCount()
//            .share()

        publishObservable.take(2).subscribe {
            Log.d(TAG, "Observer1 value : $it")
        }
        publishObservable.take(3).subscribe {
            Log.d(TAG, "Observer2 value : $it")
        }

        Thread.sleep(4000)

        publishObservable.subscribe {
            Log.d(TAG, "Observer3 value : $it")
        }
    }
}