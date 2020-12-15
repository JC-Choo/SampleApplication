package dev.chu.memo

import kotlinx.coroutines.*
import org.junit.Test
import java.util.*
import kotlin.system.measureTimeMillis

class TestThread {

    @Test
    fun threadTest() {
        testThread00()
    }

    private fun testThread00() {
        var testData = 0
        val thread00 = Thread()
        val thread01 = Thread()
        println("시작 :: testData = $testData")
        thread00.run {
            testData += 1
            println("테스트01:: testData = $testData")
        }

        thread01.run {
            testData += 2
            println("테스트01:: testData = $testData")
        }
        println("결과 :: testData = $testData")
    }

    @Test
    fun coroutineTest() {
//        coroutinesTest00()
        coroutinesTest01()
//        coroutinesTest02()
    }

    private fun coroutinesTest00() {
        println("run")
        CoroutineScope(Dispatchers.Default).launch {
            (0..10).forEach {
                val sum = (it..10).toMutableList().sum()
                println(sum)
            }
        }
        println("wait")
        runBlocking {
            delay(100L)
        }
        println("Test end")
    }

    private fun coroutinesTest01() {
        runBlocking {
            println("시작::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
            val time = measureTimeMillis {
                val jobs = ArrayList<Thread>()
                repeat(10000) {
                    jobs += Thread {
                        Thread.sleep(1000L)
                    }.also { it.start() }
                }
                println("끝::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
                jobs.forEach { it.join() }
            }
            println("Took $time ms")
        }
    }

    private fun coroutinesTest02() {
        runBlocking {
            println("시작::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
            val time = measureTimeMillis {
                val jobs = ArrayList<Job>()
                repeat(10000) {
                    jobs += launch(Dispatchers.Default) {
                        delay(1000L)
                    }
                }
                println("끝::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
                jobs.forEach { it.join() }
            }
            println("Took $time ms")
        }
    }
}