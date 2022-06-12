package com.chinalwb.myopenlanguage.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chinalwb.myopenlanguage.components.RollerNumberAnimator
import com.chinalwb.myopenlanguage.components.SpacerHeight
import com.chinalwb.myopenlanguage.components.SpacerWidth
import com.chinalwb.myopenlanguage.util.dp16
import com.chinalwb.myopenlanguage.util.dp56
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun CourseScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var startValue by remember {
            mutableStateOf(1990)
        }
        var endValue by remember {
            mutableStateOf(1990)
        }
        var decrease by remember {
            mutableStateOf(false)
        }
        RollerNumberAnimator(startValue = startValue, endValue = endValue, animateOneByOne = true, ltr = false, decrease = decrease, duration = 1000, animateToSameNumber = false) {
            Log.w("xx", "roller finished = $it")
        }
        SpacerHeight(height = dp16)

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                startValue = endValue
                endValue += 1
                decrease = false
            }) {
                Text(text = "+1")
            }

            SpacerWidth(width = dp16)

            Button(onClick = {
                startValue = endValue
                endValue -= 1
                decrease = true
            }) {
                Text(text = "-1")
            }

            SpacerWidth(width = dp16)

            Button(onClick = {
                startValue = endValue
                endValue += 10
                decrease = false
            }) {
                Text(text = "+10")
            }

            SpacerWidth(width = dp16)

            Button(onClick = {
                startValue = endValue
                endValue -= 10
                decrease = true
            }) {
                Text(text = "-10")
            }
        }


        SpacerHeight(height = dp16)

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                startValue = endValue
                endValue += Random.nextInt(1000)
                decrease = false
            }) {
                Text(text = "+random")
            }

            SpacerWidth(width = dp16)

            Button(onClick = {
                startValue = endValue
                endValue -= Random.nextInt(1000)
                decrease = true
            }) {
                Text(text = "-random")
            }
        }
    }
}