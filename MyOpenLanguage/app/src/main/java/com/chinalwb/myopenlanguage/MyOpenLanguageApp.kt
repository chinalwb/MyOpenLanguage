package com.chinalwb.myopenlanguage

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chinalwb.myopenlanguage.routing.MyOpenLanguageRouter
import com.chinalwb.myopenlanguage.routing.Screen
import com.chinalwb.myopenlanguage.screens.*
import com.chinalwb.myopenlanguage.theme.MyOpenLanguageTheme
import com.chinalwb.myopenlanguage.theme.OL_NonSelectedGray
import com.chinalwb.myopenlanguage.theme.OL_OnPrimaryGreen
import com.chinalwb.myopenlanguage.util.dp0
import com.chinalwb.myopenlanguage.util.dp40
import com.chinalwb.myopenlanguage.util.dp8


@Composable
fun MyOpenLanguageApp() {
    MyOpenLanguageTheme {
        // A surface container using the 'background' color from the theme
        AppContent()
    }
}

@Composable
private fun AppContent() {

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Crossfade(targetState = MyOpenLanguageRouter.currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            // scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent()
            },
            content = {
                MainScreenContent(
                    modifier = Modifier,
                    screenState = screenState
                )
            }
        )
    }

}

@Composable
fun BottomNavigationComponent(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember {
        mutableStateOf(0)
    }

    val items = listOf<NavigationItem>(
        NavigationItem(0, R.drawable.ic_baseline_insights_24, "学习", R.string.home_bottomNavBar_study_contentDescription),
        NavigationItem(1, R.drawable.ic_baseline_chrome_reader_mode_24, "课程", R.string.home_bottomNavBar_study_contentDescription),
        NavigationItem(2, R.drawable.ic_baseline_search_24, "查词", R.string.home_bottomNavBar_study_contentDescription),
        NavigationItem(3, R.drawable.ic_baseline_explore_24, "活动", R.string.home_bottomNavBar_study_contentDescription),
        NavigationItem(4, R.drawable.ic_baseline_person_outline_24, "我的", R.string.home_bottomNavBar_study_contentDescription)
    )


    BottomNavigation(modifier = modifier.padding(top = 12.dp)) {
        items.forEach {
            val iconModifier = if (it.index == 2) Modifier.size(36.dp) else Modifier

            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = iconModifier,
                        imageVector = ImageVector.vectorResource(id = it.vectorResourceId),
                        contentDescription = stringResource(id = it.contentDescriptionResourceId)
                    )
                },
                label = {
                    Text(text = it.text)
                },
                selected = selectedItem == it.index,
                selectedContentColor = OL_OnPrimaryGreen,
                unselectedContentColor = OL_NonSelectedGray,
                onClick = {
                    selectedItem = it.index
                }
            )
        }
    }
}

private data class NavigationItem(
    val index: Int,
    val vectorResourceId: Int,
    val text: String,
    val contentDescriptionResourceId: Int
)


@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        when (screenState.value) {
            Screen.Study -> StudyScreen(Modifier.padding(bottom = 56.dp))
            Screen.Course -> CourseScreen()
            Screen.Dict -> DictScreen()
            Screen.Activities -> ActivityScreen()
            Screen.Me -> MeScreen()
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MyOpenLanguageTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun BottomNavigationComponentPreview() {
    MyOpenLanguageTheme {
        BottomNavigationComponent()
    }
}