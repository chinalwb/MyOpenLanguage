package com.chinalwb.myopenlanguage.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.chinalwb.myopenlanguage.R
import com.chinalwb.myopenlanguage.theme.OL_OnPrimaryGreen
import com.chinalwb.myopenlanguage.util.dp24

data class FMLessonModel(
    val icon: Int = R.drawable.ic_baseline_play_circle_outline_24,
    val iconSize: Dp = dp24,
    val iconColor: Color = OL_OnPrimaryGreen,
    val title: String,
    val lessonImage: Int = R.drawable.icon_fm1
)