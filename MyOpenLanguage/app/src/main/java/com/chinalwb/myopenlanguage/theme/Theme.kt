package com.chinalwb.myopenlanguage.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = OL_Purple200,
    primaryVariant = OL_Purple700,
    secondary = OL_Teal200
)

private val LightColorPalette = lightColors(
    primary = OL_PrimaryWhite,
    onPrimary = OL_OnPrimaryGreen,
    primaryVariant = OL_Purple700,
    secondary = OL_Teal200,
    background = OL_Neutral0

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyOpenLanguageTheme(
    darkTheme: Boolean = false, // isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.background.copy(alpha = OL_AlphaNearOpaque)
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}