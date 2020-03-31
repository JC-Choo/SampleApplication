package dev.chu.memo

import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val scan = Scanner(System.`in`)

//        val n = scan.nextLine().trim().toInt()
        val n = 9

//        val ar = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()
        val ar = arrayOf(10, 20, 10, 20, 10, 50, 30, 10, 20)

        val result = sockMerchant(n, ar)

        println(result)
    }

    private fun sockMerchant(n: Int, ar: Array<Int>): Int {
        ar.sort()
        var result = 0
//        for (i in 0 until n - 1) {
//            println("ar[$i]= " + ar[i])
//            if (ar[i] == ar[i + 1]) {
//                result++
//            }
//        }
//
//        ar.forEachIndexed { index, i ->
//            if(ar[i] == ar[i+1]) {
//                if(result != result)
//                result ++
//            }
//        }

        ar.toHashSet().forEach { println("it = $it") }
//        ar.forEach { println("it = $it") }

        return result
    }
}