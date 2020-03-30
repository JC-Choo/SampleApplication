package dev.chu.memo.z_test.unit_test_bmi

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeightTipsTest {

    @Mock
    lateinit var bmi: BMI

    private var weightTips: WeightTips? = null

    @Before
    fun init() {
        weightTips = WeightTips(bmi)
    }// Read personal information from

    //Testing the underweight state
    @Test
    fun getWeightTipUnderWeightTest() {
        `when`(bmi.calculateBMI()).thenReturn(16.1f)
        assertEquals("underWeight", -1, weightTips?.getTipAboutYourWeight())
        // Read personal information from
    }

    //Testing the normal state
    @Test
    fun getWeightTipNormalTest() {
        `when`(bmi.calculateBMI()).thenReturn(19.01f)
        assertEquals("normal weight", 0, weightTips?.getTipAboutYourWeight())
        // Read personal information from
    }

    //Testing the overweight state
    @Test
    fun weightTipOverWeightTest() {
        `when`(bmi.calculateBMI()).thenReturn(26.36f)
        assertEquals("overweight", 1, weightTips?.getTipAboutYourWeight())
        // Read personal information from
    }

    //Testing the obese state
    @Test
    fun weightTipObeseTest() {
        `when`(bmi.calculateBMI()).thenReturn(32.36f)
        assertEquals("obese", 2, weightTips?.getTipAboutYourWeight())
        // Read personal information from
    }
}