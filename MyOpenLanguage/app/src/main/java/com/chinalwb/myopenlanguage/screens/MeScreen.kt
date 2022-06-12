package com.chinalwb.myopenlanguage.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chinalwb.myopenlanguage.components.A
import com.chinalwb.myopenlanguage.components.TestComponent

@Composable
fun MeScreen() {
    Column(modifier = Modifier) {
        A()

        TestComponent()
    }
}