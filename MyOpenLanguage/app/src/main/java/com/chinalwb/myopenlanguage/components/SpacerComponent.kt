package com.chinalwb.myopenlanguage.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHeight(
    modifier: Modifier = Modifier,
    height: Dp
) {
    Spacer(modifier = modifier.height(height))
}

@Composable
fun SpacerWidth(
    modifier: Modifier = Modifier,
    width: Dp
) {
    Spacer(modifier = modifier.width(width))
}