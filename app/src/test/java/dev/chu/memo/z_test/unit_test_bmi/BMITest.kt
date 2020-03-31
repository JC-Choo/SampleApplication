package dev.chu.memo.z_test.unit_test_bmi

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BMITest {

    private lateinit var bmi: BMI

    @Before
    fun setUp() {
        bmi = BMI(68f, 1.67f)
    }

    @Test
    fun calculateBMI() {
        val expectedValue = 24.38f
        assertEquals(
            "BMI calculating is correct ",
            expectedValue.toDouble(),
            bmi.calculateBMI().toDouble(),
            0.01
        )
    }
}