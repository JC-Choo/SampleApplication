package dev.chu.memo.z_test.rxjava_200321

import android.annotation.SuppressLint
import android.util.Log
import dev.chu.memo.etc.extension.TAG
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

@SuppressLint("CheckResult")
object ExampleSubject {
    fun exampleAsyncSubject() {
        val subject = AsyncSubject.create<String>()
        subject.onNext("true")        // 이벤트 발행(subscribe에서 발행하지 않고 onNext()에서 이미 발행)
        subject.subscribe()             // 이벤트를 발행했기 때문에 true를 받지 못함!

        subject.subscribe() // observable1
        subject.onNext("true")  // observable1은 true를 받는다!

        subject.subscribe {
            Log.d(TAG, "value : $it")
        }
        subject.onNext("true")
        subject.onNext("false")
        subject.onComplete()
    }

    fun examplePublish() {
        val subject = PublishSubject.create<Int>()
        subject.onNext(0)
        subject.subscribe {
            Log.d(TAG, "Observer1 value : $it")
        }
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe {
            Log.d(TAG, "Observer2 value : $it")
        }
        subject.onNext(3)
        subject.onNext(4)
        subject.onComplete()
        // Observer1 1/2
        // Observer1 3/4
        // Observer2 3/4
    }

    fun exampleBehavior() {
        val subject = BehaviorSubject.create<Int>()
        subject.onNext(-1)
        subject.onNext(0)
        subject.subscribe {
            Log.d(TAG, "value : $it")
        }
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onComplete()
    }

    fun exampleReplay() {
        val subject = ReplaySubject.create<Int>()
        subject.onNext(-2)
        subject.onNext(-1)
        subject.onNext(0)
        subject.subscribe {
            Log.d(TAG, "value : $it")
        }
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onComplete()
    }
}