package com.lakshaykamat.bmiCalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lakshaykamat.bmiCalculator.data.BMI
import com.lakshaykamat.bmiCalculator.data.BmiResult
import com.lakshaykamat.bmiCalculator.ui.theme.BMICalculatorTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    haveBackButton: Boolean,
    actionButton: @Composable () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            if (haveBackButton) {
                IconButton(
                    onClick = { /* Handle navigation icon click */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            // Custom settings button
            actionButton()
        }
    )
}


@Composable
fun BmiApp(modifier: Modifier = Modifier) {
    var weightInputState by remember { mutableStateOf("") }
    var heightInputState by remember { mutableStateOf("") }

    var bmi by remember { mutableStateOf<BmiResult?>(null) }
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        //Top App Bar
        AppTopBar(
            title = "BMI Calculator",
            haveBackButton = false,
            actionButton = {
                IconButton(
                    onClick = { /* Handle custom action click */ }
                ) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                }
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Weight Input Field
            TextField(
                label = { Text(text = "Kilograms") },
                value = weightInputState,
                onValueChange = { weightInputState = it })
            Margin(value = 10, isVertical = true, isHorizontal = false)


            //Height Input Field
            TextField(
                label = { Text(text = "Metre") },
                value = heightInputState,
                onValueChange = { heightInputState = it })
            Margin(value = 10, isVertical = true, isHorizontal = false)


            //Calculate Button
            Button(modifier = Modifier.fillMaxWidth(.5f), onClick = {
                bmi = BMI.performBmiOperation(
                    weightInputState,
                    heightInputState,
                    gender = BMI.Gender.Male
                )

            }) {
                Text(text = "Calculate")
            }
            Margin(value = 20, isVertical = true, isHorizontal = false)

            //Result
            if (bmi !== null) {
                Text(
                    text = "Body Mass Index: ${bmi!!.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = bmi!!.category, fontSize = 18.sp, fontWeight = FontWeight.Normal)
            }

        }
    }

}

@Composable
fun Margin(value: Int, isVertical: Boolean, isHorizontal: Boolean) {
    if (isVertical) {
        Spacer(modifier = Modifier.padding(vertical = value.dp))
    } else if (isHorizontal) {
        Spacer(modifier = Modifier.padding(horizontal = value.dp))
    } else {
        Spacer(modifier = Modifier.padding(value.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    BMICalculatorTheme {
        AppTopBar(title = "BMI Calculator", haveBackButton = true) {
            IconButton(
                onClick = { /* Handle custom action click */ }
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            }
        }
    }
}