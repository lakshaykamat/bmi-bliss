package com.lakshaykamat.bmiBliss.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lakshaykamat.bmiBliss.R
import com.lakshaykamat.bmiBliss.viewModel.BMI
import com.lakshaykamat.bmiBliss.data.Bmi
import com.lakshaykamat.bmiBliss.ui.theme.BMICalculatorTheme


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

    var selectedGender by remember { mutableStateOf<BMI.Gender?>(null) }

    var bmi by remember { mutableStateOf<Bmi?>(null) }

    val (inMetricUnits, setIsMetricUnits) = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Top App Bar
        AppTopBar(
            title = stringResource(R.string.app_name),
            haveBackButton = false,
            actionButton = {
                IconButton(
                    onClick = { /* Handle custom action click */ }
                ) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                }
            }
        )
        Column(modifier = Modifier.width(270.dp)) {
            //Weight Input Field
            TextField(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.weight_fill_icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = if (inMetricUnits) "Kilogram" else "Pounds") },
                value = weightInputState,
                onValueChange = { weightInputState = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Margin(value = 10, isVertical = true, isHorizontal = false)


            //Height Input Field
            TextField(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.height_fill_icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = if (inMetricUnits) "Centimetre" else "Feet") },
                value = heightInputState,
                onValueChange = { heightInputState = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Margin(value = 10, isVertical = true, isHorizontal = false)



            Column {
                //Radio Buttons for gender
                Row(
                    // modifier = Modifier.padding(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Gender", style = MaterialTheme.typography.bodyLarge)
                    BMI.Gender.entries.forEach { gender ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (gender == selectedGender),
                                onClick = { selectedGender = gender }
                            )
                            Text(
                                text = gender.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Metric Units")
                    Switch(
                        checked = inMetricUnits,
                        onCheckedChange = { newCheckedState ->
                            setIsMetricUnits(newCheckedState)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Margin(value = 10, isVertical = true, isHorizontal = false)


            //Calculate Button
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                if (selectedGender != null) {
                    bmi = BMI.performBmiOperation(
                        weightInputState,
                        heightInputState,
                        gender = selectedGender!!,
                        inMetricUnits
                    )
                }

            }) {
                Text(text = "Calculate")
            }
            Margin(value = 20, isVertical = true, isHorizontal = false)

            //Result
            if (bmi !== null) {
                Text(
                    text = "Body Mass Index: ${bmi!!.bmi}",
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