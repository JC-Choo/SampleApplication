package dev.chu.splash_screen

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testCollectionOrSequence() {
        val list00 = (1..10)
            .filter {
                println("filter $it")
                it%2 == 1
            }
            .map {
                println("map $it")
                it*2
            }
            .take(2)
        list00.forEach { print("$it ") }
        println()
        println("------------")

        val list01 = (1..10).asSequence()
            .filter {
                println("filter $it")
                it%2 == 1
            }
            .map {
                println("map $it")
                it*2
            }
            .take(2)
            .toList()
        list01.forEach { print("$it ") }
        println()
        println("------------")
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}