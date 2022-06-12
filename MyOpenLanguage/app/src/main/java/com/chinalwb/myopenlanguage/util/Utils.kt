package com.chinalwb.myopenlanguage.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun screenWidthDp() = LocalConfiguration.current.screenWidthDp

@Composable
fun screenHeightDp() = LocalConfiguration.current.screenHeightDp