package dev.chu.memo

import dev.chu.memo.etc.hash_map.BasicHashMap
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testHashMap() {
        val aHashMap = BasicHashMap<String, String>()

        aHashMap.put("A", "Alpha")
        aHashMap.put("B", "Bravo")
        aHashMap.put("C", "Charlie")
        aHashMap.put("D", "Delta")
        aHashMap.put("E", "Echo")
        aHashMap.put("F", "Foxtrot")
        aHashMap.put("G", "Golf")
        aHashMap.put("H", "Hotel")
        aHashMap.put("I", "India")
        aHashMap.put("J", "July")
        aHashMap.put("K", "Kilo")
        aHashMap.put("L", "Lima")
        aHashMap.put("M", "Mike")
        aHashMap.put("N", "November")
        aHashMap.put("O", "Oscar")
        aHashMap.put("P", "Papa")
        aHashMap.put("Q", "Quebec")
        aHashMap.put("R", "Romeo")
        aHashMap.put("S", "Sierra")
        aHashMap.put("T", "Tango")
        aHashMap.put("U", "Uniform")
        aHashMap.put("V", "Victor")
        aHashMap.put("W", "Whiskey")
        aHashMap.put("X", "X-Ray")
        aHashMap.put("Y", "Yankee")
        aHashMap.put("Z", "Zulu")

        for (key in 'A'..'Z') {
            println("Key = [$key] value =[${aHashMap.get(key.toString())}]")
        }

        println()
        println(aHashMap.toString())
    }
}