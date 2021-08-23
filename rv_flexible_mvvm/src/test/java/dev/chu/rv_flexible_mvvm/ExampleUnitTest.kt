package dev.chu.rv_flexible_mvvm

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private fun String.iso8601ToDate(): Date =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).run {
            timeZone = TimeZone.getTimeZone("UTC")
            parse(this@iso8601ToDate) ?: throw IllegalArgumentException("Parse failed!")
        }

    private fun String.deleteZero(): String {
        val date = this.split(".")
        val mm = if (date[0].contains("0")) {
            date[0][1]
        } else date[0]
        val dd = if (date[1].contains("0")) {
            date[1][1]
        } else date[1]

        return "$mm.$dd"
    }

    private fun String.deleteZero2(): String {
        val date = this.split(".")
        val mm = date[0].toInt()
        val dd = date[1].toInt()
        return "$mm.$dd"
    }

    private fun String.convertTimeToAmOrPm(): String {
        val time = this.split(":")
        val hour = time[0].toInt()
        val amOrPm = if (hour >= 12)  {
            "PM ${if (hour == 12) hour else hour-12 }"
        } else {
            "AM $hour"
        }
        return "$amOrPm ${time[1]}"
    }

    @Test
    fun testDate() {
//        val list = mutableListOf<String>()
        val testDate = "2021-07-21T03:54:32Z"
        val result00 = testDate.iso8601ToDate()
        println("result = $result00")

        testDate.let {
            val dateYYYYMMDD = SimpleDateFormat(
                "yyyy.MM.dd",
                Locale.US
            ).format(result00)
            println("dateYYYYMMDD = $dateYYYYMMDD")
//            list.add(dateYYYYMMDD)
//            list.add(dateYYYYMMDD)
//            list.add(dateYYYYMMDD)

            val dateMMDD = SimpleDateFormat(
                "MM.dd",
                Locale.US
            ).format(it.iso8601ToDate())
            println("dateMMDD = $dateMMDD")
//            list.add(dateMMDD)

            val time = SimpleDateFormat(
                "HH.mm",
                Locale.US
            ).format(it.iso8601ToDate())
            println("time = $time")
//            list.add(time)


//            list.add(dateYYYYMMDD)
//            list.add(dateMMDD)
//            list.add(time)
        }

//        println("-------------")
//        list.forEach { println(it) }
//        println("-------------")
//        list.map {
//            it
//        }.distinct().forEach { println(it) }
//        println("-------------")


        println("dateMMDD = ${"07.02".deleteZero2()}")
        println("dateMMDD = ${"01.03".deleteZero2()}")
        println("dateMMDD = ${"01.3".deleteZero2()}")
        println("dateMMDD = ${"1.03".deleteZero2()}")

        println("time = ${"12:23".convertTimeToAmOrPm()}")
        println("time = ${"01:01".convertTimeToAmOrPm()}")
        println("time = ${"00:00".convertTimeToAmOrPm()}")
        println("time = ${"24:00".convertTimeToAmOrPm()}")
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)


        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
        println("random = ${Random.nextInt(0, 3)}")
    }
}