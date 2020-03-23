package dev.chu.memo

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val test01 = "aaa"
        val test02 = "aaa@"
        val test03 = "aaa@a"
        val test04 = "aaa@aa"
        val test05 = "aaa@aaa"
        val test06 = "aaa@a.a"
        val test07 = "aaa@aa.a"
        val test08 = "aaa@aaa.a"
        val test09 = "aaa@a.aa"
        val test10 = "aaa@aa.aa"
        val test11 = "aaa@aaa.aa"

        println("test01 = ${test01.isEmailValid()}")
        println("test02 = ${test02.isEmailValid()}")
        println("test03 = ${test03.isEmailValid()}")
        println("test04 = ${test04.isEmailValid()}")
        println("test05 = ${test05.isEmailValid()}")
        println("test06 = ${test06.isEmailValid()}")
        println("test07 = ${test07.isEmailValid()}")
        println("test08 = ${test08.isEmailValid()}")
        println("test09 = ${test09.isEmailValid()}")
        println("test10 = ${test10.isEmailValid()}")
        println("test11 = ${test11.isEmailValid()}")

//        val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
//        val jsonAgain = json?.toString() // "{"key": "value"}"
//        val stringFromJson = json?.getString("key") // "value"
//        println("json = $json")
//        println("jsonAgain = $jsonAgain")
//        println("stringFromJson = $stringFromJson")


        val ccFormatted = "1234567890123456".creditCardFormatted // "1234 5678 9012 3456"
        println("ccFormatted = $ccFormatted")
    }
}

fun String.isEmailValid(): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

val String.jsonObject: JSONObject?
    get() = try {
        JSONObject(this)
    } catch (e: JSONException) {
        null
    }

val String.jsonArray: JSONArray?
    get() = try {
        JSONArray(this)
    } catch (e: JSONException) {
        null
    }

val String.creditCardFormatted: String
    get() {
        val preparedString = replace(" ", "").trim()
        val result = StringBuilder()
        for (i in preparedString.indices) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ")
            }
            result.append(preparedString[i])
        }
        return result.toString()
    }