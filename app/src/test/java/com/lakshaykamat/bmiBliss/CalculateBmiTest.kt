package com.lakshaykamat.bmiBliss

import com.lakshaykamat.bmiBliss.data.BMI
import com.lakshaykamat.bmiBliss.data.BmiResult
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculateBmiTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCalculateBMIForMale() {
        val weight = "70.0" // in kilograms
        val height = "1.75" // in meters
        val gender = BMI.Gender.Male

        val result: BmiResult? = BMI.performBmiOperation(weight, height, gender)

        // You may adjust these values based on your expected results
        assertEquals(23.86, result!!.value, 0.01)
        assertEquals("Normal weight", result.category)
    }

    @Test
    fun testCalculateBMIForFemale() {
        val weight = "70.0" // in kilograms
        val height = "1.75" // in meters
        val gender = BMI.Gender.Female

        val result: BmiResult? = BMI.performBmiOperation(weight, height, gender)

        // You may adjust these values based on your expected results
        assertEquals(21.86, result!!.value, 0.01)
        assertEquals("Normal weight", result.category)
    }

}