package com.chinalwb.myopenlanguage.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class CourseModel(
    val backgroundColor: Color,
    val courseIconResId: Int,
    val courseName: String,
    val lessonTitle: String,
    val lessonDesc: String,
    val actionText: String,
    val backgroundImageResId: Int? = null,
    val pinnedImageResId: Int? = null,
    val lessonDescSubtitle: String? = null
)