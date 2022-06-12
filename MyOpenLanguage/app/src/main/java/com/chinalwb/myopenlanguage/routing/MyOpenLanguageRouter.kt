package com.chinalwb.myopenlanguage.routing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object Study : Screen()
    object Course: Screen()
    object Dict: Screen()
    object Activities: Screen()
    object Me : Screen()
}

object MyOpenLanguageRouter {
    val currentScreen: MutableState<Screen> = mutableStateOf(
        Screen.Study
    )

    private var previousScreen: MutableState<Screen> = mutableStateOf(
        Screen.Study
    )

    fun navigateTo(destination: Screen) {
        previousScreen.value = currentScreen.value
        currentScreen.value = destination
    }

    fun goBack() {
        currentScreen.value = previousScreen.value
    }

}