package com.lakshaykamat.bmiCalculator.data

data class BmiResult(val value: Double, val category: String)

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
    private fun calculateBMI(weight: Double, height: Double, gender: Gender): BmiResult {
        // Calculate BMI with gender adjustment
        val bmi = calculateBmiForGender(weight, height, if (gender == Gender.Female) -1.0 else 1.0)

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
    fun performBmiOperation(weight: String, height: String, gender: Gender): BmiResult? {
        // Convert weight and height to Double
        val parsedWeight = weight.toDoubleOrNull()
        val parsedHeight = height.toDoubleOrNull()

        // Check if conversion is successful
        if (parsedWeight != null && parsedHeight != null) {
            // Perform BMI calculation
            return calculateBMI(parsedWeight, parsedHeight, gender)
        }

        // Return null if weight or height is not a valid number
        return null
    }

    /**
     * Calculates BMI with gender adjustment.
     * @param weight Weight in kilograms.
     * @param height Height in meters.
     * @param genderAdjustment Adjustment value based on gender (1.0 for Male, -1.0 for Female).
     * @return Calculated BMI value.
     */
    private fun calculateBmiForGender(weight: Double, height: Double, genderAdjustment: Double): Double {
        // Calculate BMI and format to two decimal places
        val bmi = (weight / (height * height)) + genderAdjustment
        return "%.2f".format(bmi).toDouble()
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
