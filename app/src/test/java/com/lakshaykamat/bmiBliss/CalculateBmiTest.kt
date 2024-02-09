package com.lakshaykamat.bmiBliss

import com.lakshaykamat.bmiBliss.viewModel.BMI
import com.lakshaykamat.bmiBliss.data.BmiResult
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

data class BmiTest(
    val weight: String,
    val height: String,
    val isMetricUnits: Boolean,
    val gender: BMI.Gender,
    val expectedBmi: Int,
    val expectedBmiCategory:String
)

class CalculateBmiTest {

    @Test
    fun `bmi in metric units for male`() {
        val bmi = BmiTest(
            weight = "70",
            height = "175",
            isMetricUnits = true,
            gender = BMI.Gender.Male,
            expectedBmi = 23,
            expectedBmiCategory = "Normal weight"
        )

        val result: BmiResult? =
            BMI.performBmiOperation(bmi.weight, bmi.height, bmi.gender, bmi.isMetricUnits)

        if (result != null) {
            assertEquals(bmi.expectedBmiCategory, result.category)
            assertEquals(bmi.expectedBmi, result.value)
        }
    }

    @Test
    fun `bmi in metric units for female`() {
        val bmi = BmiTest(
            weight = "70",
            height = "175",
            isMetricUnits = true,
            gender = BMI.Gender.Female,
            expectedBmi = 23,
            expectedBmiCategory = "Normal weight"
        )

        val result: BmiResult? =
            BMI.performBmiOperation(bmi.weight, bmi.height, bmi.gender, bmi.isMetricUnits)

        if (result != null) {
            assertEquals(bmi.expectedBmi, result.value)
            assertEquals(bmi.expectedBmiCategory, result.category)
        }
    }

}