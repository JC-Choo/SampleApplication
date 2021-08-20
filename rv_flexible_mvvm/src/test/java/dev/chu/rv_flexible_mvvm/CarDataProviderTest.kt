package dev.chu.rv_flexible_mvvm

import org.junit.Test

class CarDataProviderTest {

    private val carDataProvider = CarDataProvider()

    @Test
    fun testProvider() {
        val carList = carDataProvider.getCarListData()
        val carsByMake = carList.groupBy { it.make }
        carList.forEachIndexed { index, it ->
            println("carList_$index = $it")
        }
        carsByMake.run {

        }
        carsByMake.entries.forEachIndexed { index, entry ->
            println("carsByMake_$index = $entry")
        }
    }
}