/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.chinalwb.myopenlanguage.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chinalwb.myopenlanguage.theme.OL_OnPrimaryGreen
import com.chinalwb.myopenlanguage.theme.OL_PrimaryWhite
import kotlinx.coroutines.delay

val scenarioList = listOf(
    "做家务时",
    "下班路上",
    "坐地铁时",
    "散步时听",
    "开车时听",
    "放空时听",
    "通勤时听",
    "洗漱时听"
)

val carouselList = listOf(
    "Hello - 1",
    "World - 2",
    "Java - 3",
    "Kotlin - 4",
    "C++ - 5",
    "Ruby - 6",
    "Rust - 7",
    "Swift - 8"
)


@Composable
fun TextCarousel(
    modifier: Modifier = Modifier,
    textList: List<String>,
    carouselHeight: Dp,
    carouselWidth: Dp? = null,
    innerPaddingVertical: Dp = 0.dp,
    swapInterval: Long = 3000,
    animationDurationMills: Int = 300,
    animationEasing: Easing = LinearEasing,
    useFading: Boolean = true,
    clipShape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = OL_OnPrimaryGreen,
    textColor: Color = OL_PrimaryWhite,
    textSize: TextUnit = 12.sp,
    textPadding: Dp = 2.dp
) {
    if (textList.size < 2) {
        throw IllegalArgumentException("textList size must be more than 2.")
    }
    var topVisible by remember {
        mutableStateOf(true)
    }
    var bottomVisible by remember {
        mutableStateOf(false)
    }
    var topTextIndex = 0
    var bottomTextIndex = 1
    var topText by remember {
        mutableStateOf(textList[0])
    }
    var bottomText by remember {
        mutableStateOf(textList[1])
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(swapInterval)
            topVisible = !topVisible
            bottomVisible = !bottomVisible

            if (topVisible) {
                topTextIndex = if (bottomTextIndex == textList.lastIndex)
                    0
                else bottomTextIndex + 1
                topText = textList[topTextIndex]
            } else if (bottomVisible) {
                bottomTextIndex = if (topTextIndex == textList.lastIndex) {
                    0
                } else {
                    topTextIndex + 1
                }
                bottomText = textList[bottomTextIndex]
            }
        }
    })

    var enterTransition: EnterTransition = slideInVertically(
        animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing)
    ) { it }
    if (useFading) {
        enterTransition = fadeIn(
            animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing),
        ) + enterTransition
    }

    var exitTransition: ExitTransition = slideOutVertically(
        animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing)
    ) { it * -1 }
    if (useFading) {
        exitTransition = fadeOut(
            animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing),
        ) + exitTransition
    }

    Box(
        modifier = modifier
            .height(carouselHeight)
            .clip(RoundedCornerShape(0.dp))
    ) {
        AnimatedVisibility(
            visible = topVisible,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(carouselHeight)
                    .padding(vertical = innerPaddingVertical)
                    .clip(clipShape)
                    .background(backgroundColor)
            ) {
                var textModifier = Modifier.padding(horizontal = textPadding)
                carouselWidth?.run {
                    textModifier = textModifier.width(this)
                }

                Text(
                    modifier = textModifier,
                    text = topText,
                    color = textColor,
                    fontSize = textSize,
                    textAlign = TextAlign.Center
                )
            }
        }

        AnimatedVisibility(
            visible = bottomVisible,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(carouselHeight)
                    .padding(vertical = innerPaddingVertical)
                    .clip(clipShape)
                    .background(backgroundColor)
            ) {
                var textModifier  = Modifier.padding(horizontal = textPadding)
                carouselWidth?.run {
                    textModifier = textModifier.width(this)
                }

                Text(
                    modifier = textModifier,
                    text = bottomText,
                    color = textColor,
                    fontSize = textSize,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TextCarousel(
    modifier: Modifier = Modifier,
    textList: List<String>,
    carouselHeight: Dp,
    carouselWidth: Dp? = null,
    drawCurrent: @Composable (Int) -> Unit,
    drawNext: @Composable (Int) -> Unit,
    innerPaddingVertical: Dp = 0.dp,
    swapInterval: Long = 3000,
    animationDurationMills: Int = 300,
    animationEasing: Easing = LinearEasing,
    useFading: Boolean = true,
    clipShape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = OL_OnPrimaryGreen,
    textColor: Color = OL_PrimaryWhite,
    textSize: TextUnit = 12.sp,
    textPadding: Dp = 2.dp
) {
    if (textList.size < 2) {
        throw IllegalArgumentException("textList size must be more than 2.")
    }
    var topVisible by remember {
        mutableStateOf(true)
    }
    var bottomVisible by remember {
        mutableStateOf(false)
    }
    var topTextIndex by remember {
        mutableStateOf(0)
    }
    var bottomTextIndex by remember {
        mutableStateOf(1)
    }
    var topText by remember {
        mutableStateOf(textList[0])
    }
    var bottomText by remember {
        mutableStateOf(textList[1])
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(swapInterval)
            topVisible = !topVisible
            bottomVisible = !bottomVisible

            if (topVisible) {
                topTextIndex = if (bottomTextIndex == textList.lastIndex)
                    0
                else bottomTextIndex + 1
                topText = textList[topTextIndex]
            } else if (bottomVisible) {
                bottomTextIndex = if (topTextIndex == textList.lastIndex) {
                    0
                } else {
                    topTextIndex + 1
                }
                bottomText = textList[bottomTextIndex]
            }
        }
    })

    var enterTransition: EnterTransition = slideInVertically(
        animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing)
    ) { it }
    if (useFading) {
        enterTransition = fadeIn(
            animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing),
        ) + enterTransition
    }

    var exitTransition: ExitTransition = slideOutVertically(
        animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing)
    ) { it * -1 }
    if (useFading) {
        exitTransition = fadeOut(
            animationSpec = tween(durationMillis = animationDurationMills, easing = animationEasing),
        ) + exitTransition
    }

    Box(
        modifier = modifier
            .height(carouselHeight)
            .clip(RoundedCornerShape(0.dp))
    ) {
        AnimatedVisibility(
            visible = topVisible,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(carouselHeight)
                    .padding(vertical = innerPaddingVertical)
                    .clip(clipShape)
                    .background(backgroundColor)
            ) {
                Log.w("xx", "topTextIndex = $topTextIndex")
                drawCurrent(topTextIndex)
                // drawCurrentComposable(textPadding, carouselWidth, topText, textColor, textSize)
            }
        }

        AnimatedVisibility(
            visible = bottomVisible,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(carouselHeight)
                    .padding(vertical = innerPaddingVertical)
                    .clip(clipShape)
                    .background(backgroundColor)
            ) {
                // drawCurrentComposable(textPadding, carouselWidth, bottomText, textColor, textSize)
                drawNext(bottomTextIndex)
            }
        }
    }
}

@Composable
fun drawCurrentComposable(
    topText: String,
    carouselWidth: Dp? = null,
    textColor: Color = OL_PrimaryWhite,
    textSize: TextUnit = 12.sp,
    textPadding: Dp = 2.dp
) {
    var textModifier = Modifier.padding(horizontal = textPadding)
    carouselWidth?.run {
        textModifier = textModifier.width(this)
    }

    Text(
        modifier = textModifier,
        text = topText,
        color = textColor,
        fontSize = textSize,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun TextCarouselPreview() {
    Box(modifier = Modifier.size(300.dp)) {
        TextCarousel(
            modifier = Modifier,
            textList = carouselList,
            carouselHeight = 32.dp,
            carouselWidth = 100.dp,
            drawCurrent = {
                drawCurrentComposable(
                    carouselWidth = 100.dp,
                    topText = carouselList[it]
                )
            },
            drawNext = {
                drawCurrentComposable(
                    carouselWidth = 100.dp,
                    topText = carouselList[it]
                )
            }
        )
    }
}