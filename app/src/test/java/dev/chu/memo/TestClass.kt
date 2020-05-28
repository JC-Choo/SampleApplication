package dev.chu.memo

import org.junit.Test

class TestClass {
    @Test
    fun testCollection() {
        // https://medium.com/@elye.project/kotlin-collection-functions-cheat-sheet-975371a96c4b
        val a = listOf(1, 2, 3, 4)
        val resultA = a.reduce { result, item -> result*item }
        println("resultA = $resultA")

        val b = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val resultB = b.chunked(3)
        resultB.forEach { list ->
            println("list = $list")
            list.forEach {
                print("$it ")
            }
            println()
        }

        val c = listOf(1, 2, 3)
        val resultC1 = c.sum()
        val resultC2 = c.map { it*3 }.sum()
        println("resultC = $resultC1, $resultC2")
    }
}