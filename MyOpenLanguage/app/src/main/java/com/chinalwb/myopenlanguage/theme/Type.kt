package com.chinalwb.myopenlanguage.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.chinalwb.myopenlanguage.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)

private val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal),
    Font(R.font.karla_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        // fontFamily = Montserrat,
        fontSize = 40.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 59.sp
    ),
    h2 = TextStyle(
        // fontFamily = Montserrat,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 37.sp
    ),
    h3 = TextStyle(
        // fontFamily = Montserrat,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 29.sp,
        letterSpacing = 0.25.sp

    ),
    h4 = TextStyle(
        // fontFamily = Montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),
    h5 = TextStyle(
        // fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        textAlign = TextAlign.Left
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)