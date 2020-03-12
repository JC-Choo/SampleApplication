package dev.chu.memo

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val isTrue = true
        val isFalse = false

        println("true && true = ${isTrue && isTrue}")
        println("true && false = ${isTrue && isFalse}")
        println("true || false = ${isTrue || isFalse}")
        println("false || false = ${isFalse || isFalse}")
    }
}