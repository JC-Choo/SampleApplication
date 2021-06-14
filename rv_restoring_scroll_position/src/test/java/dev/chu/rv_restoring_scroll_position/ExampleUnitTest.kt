package dev.chu.rv_restoring_scroll_position

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testList() {
        val test00 = listOf(1..8).flatten()
        test00.forEach { print("$it ") }
        println()

        val test01 = (1..8).toList()
        test01.forEach { print("$it ") }
        println()
    }
}