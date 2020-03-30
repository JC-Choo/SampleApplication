package dev.chu.memo.z_test.unit_test_bmi

class WeightTips(private var bmi: BMI) {
    fun getTipAboutYourWeight(): Int {
        val bmiValue = bmi.calculateBMI()
        return when {
            bmiValue < 18.5f -> {
                // under weight
                -1
            }
            bmiValue in 18.5..24.9 -> {
                // Normal
                0
            }
            bmiValue in 25.0..29.9 -> {
                // OverWeight
                1
            }
            else -> {
                // Obese
                2
            }
        }
    }
}