package com.lakshaykamat.bmiBliss.viewModel

import com.lakshaykamat.bmiBliss.data.BmiResult
import kotlin.math.roundToInt



object BMI {
    enum class Body {
        Underweight, Normalweight, MarginallyOverweight, Overweight, Obese
    }

    enum class Gender { Male, Female }

    /**
     * Calculates BMI based on weight, height, and gender adjustment.
     * @param weight Weight in kilograms.
     * @param height Height in meters.
     * @param gender Gender of the person (Male/Female).
     * @return BmiResult containing BMI value and corresponding category.
     */
    private fun calculateBMI(
        weight: Double,
        height: Double,
        gender: Gender,
        inMetricUnits: Boolean
    ): BmiResult {
        // Calculate BMI with gender adjustment
        val bmi = if (inMetricUnits) {
            calculateBmiWithMetricUnits(
                weight,
                height,
                if (gender == Gender.Female) 0.0 else 0.0
            )
        } else {
            calculateBmiWithMetricUnits(
                (weight / 2.205),
                (height * 30.48),
                if (gender == Gender.Female) 0.0 else 0.0
            )
        }


        // Determine BMI category based on calculated BMI
        val category = when {
            bmi < 18.5 -> Body.Underweight
            bmi < 24.9 -> Body.Normalweight
            bmi < 29.9 -> Body.MarginallyOverweight
            bmi < 34.9 -> Body.Overweight
            else -> Body.Obese
        }

        return BmiResult(bmi, getBMIStringCategory(category))
    }

    /**
     * Performs BMI calculation based on weight, height, and gender.
     * @param weight String representation of weight.
     * @param height String representation of height.
     * @param gender Gender of the person (Male/Female).
     * @return BmiResult if calculation is successful, otherwise null.
     */
    fun performBmiOperation(
        weight: String,
        height: String,
        gender: Gender,
        inMetricUnits: Boolean
    ): BmiResult? {
        // Convert weight and height to Double
        val parsedWeight = weight.toDoubleOrNull()
        val parsedHeight = height.toDoubleOrNull()

        // Check if conversion is successful
        if (parsedWeight != null && parsedHeight != null) {
            // Perform BMI calculation
            return calculateBMI(parsedWeight, parsedHeight, gender, inMetricUnits)
        }

        // Return null if weight or height is not a valid number
        return null
    }

    /**
     * Calculates BMI with gender adjustment.
     * @param weightInKg Weight in kilograms.
     * @param heightInCm Height in centimeter.
     * @param genderAdjustment Adjustment value based on gender (0.5 for Male, -0.5 for Female).
     * @return Calculated BMI value.
     */
    private fun calculateBmiWithMetricUnits(
        weightInKg: Double,
        heightInCm: Double,
        genderAdjustment: Double
    ): Int {
        val heightInInch = heightInCm / 100.0
        val bmi = (weightInKg / (heightInInch * heightInInch)) + genderAdjustment
        return bmi.roundToInt()
    }


    /**
     * Returns the BMI category as a string based on the provided Body enum.
     * @param category BMI category (Normal weight, Overweight, Obese, Underweight, MarginallyOverweight).
     * @return String representation of the BMI category.
     */
    private fun getBMIStringCategory(category: Body): String {
        return when (category) {
            Body.Normalweight -> "Normal weight"
            Body.Overweight -> "Overweight"
            Body.Obese -> "Obese"
            Body.Underweight -> "Underweight"
            Body.MarginallyOverweight -> "Marginally Overweight"
        }
    }
}
