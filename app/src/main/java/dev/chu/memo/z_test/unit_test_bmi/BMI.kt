package dev.chu.memo.z_test.unit_test_bmi

class BMI(private var weight: Float, private var height: Float) {
    fun calculateBMI(): Float =
        (weight/(height*height))
}