package com.chinalwb.myopenlanguage.components

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.chinalwb.myopenlanguage.R
import com.chinalwb.myopenlanguage.screens.FMPlayState
import com.chinalwb.myopenlanguage.theme.MyOpenLanguageTheme
import com.chinalwb.myopenlanguage.theme.OL_Gray
import com.chinalwb.myopenlanguage.theme.OL_LightGray3
import com.chinalwb.myopenlanguage.theme.OL_LightGray5
import com.chinalwb.myopenlanguage.util.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ClearRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.0f,
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AudioPlayComponent(
    modifier: Modifier = Modifier,
    playState: FMPlayState,
    fmImageRotationZ: Float = 0f,
    onClickPlayIcon: () -> Unit,
    onClickPauseIcon: () -> Unit,
    onClickStopIcon: () -> Unit,
    onRotationUpdated: (newRotationZ: Float) -> Unit = {}
) {

    val imageSize = 48
    val topPadding = 4
    val bottomPadding = 8
    val totalHeight = imageSize + topPadding + bottomPadding
    val hPadding = 16
    val textGuideLinePos = imageSize + hPadding + hPadding / 2
    val background = OL_LightGray5
    val rotationSpeed = 60f // 60 degrees per second
    val rotationInterval = 10L // update the rotation per 10 ms

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }
    var imageRotationZ by remember {
        mutableStateOf(fmImageRotationZ)
    }
    var imageRotationStarted by remember {
        mutableStateOf(false)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            onRotationUpdated(imageRotationZ)
        }
    }

    fun startRotatingImage() {
        if (imageRotationStarted) {
            return
        }
        imageRotationStarted = true
        job = scope.launch {
            while (true) {
                imageRotationZ += rotationSpeed / (1000 / rotationInterval)
                delay(rotationInterval)
            }
        }
    }

    fun stopRotatingImage() {
        if (!imageRotationStarted) {
            return
        }
        imageRotationStarted = false
        job?.cancel()
    }

    LaunchedEffect(key1 = playState, block = {
        if (playState == FMPlayState.PLAYING) {
            startRotatingImage()
        } else {
            stopRotatingImage()
        }
    })



    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(dp(totalHeight))
    ) {
        val (
            containerRef,
            imageRef,
            titleRef,
            timerRef,
            actionRef,
        ) = createRefs()

        val horizontalGuideLine = createGuidelineFromTop(dp(topPadding))


        Box(modifier = Modifier
            .background(background)
            .fillMaxWidth()
            .constrainAs(containerRef) {
                top.linkTo(horizontalGuideLine)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }) {
        }

        val verticalGuideLineForImage = createGuidelineFromStart(dp(hPadding))

//        val rotateAnimation = rememberInfiniteTransition()
//        var rotationZ = rotateAnimation.animateFloat(
//            initialValue = 0f,
//            targetValue = 360f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(6000, delayMillis = 0, easing = LinearEasing),
//                repeatMode = RepeatMode.Restart
//            )
//        )
        Image(
            modifier = Modifier
                .size(dp(imageSize))
                .clip(CircleShape)
                .graphicsLayer { rotationZ = imageRotationZ }
                .constrainAs(imageRef) {
                    start.linkTo(verticalGuideLineForImage)
                    top.linkTo(parent.top)
                },
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = "desc",
            contentScale = ContentScale.Crop
        )

        val verticalGuideLineForText = createGuidelineFromStart(dp(textGuideLinePos))

        TextComponent(
            modifier = Modifier
                .padding(top = dp2)
                .constrainAs(titleRef) {
                    start.linkTo(verticalGuideLineForText)
                    end.linkTo(actionRef.start)
                    top.linkTo(containerRef.top)
                    width = Dimension.fillToConstraints
                },
            text = "外国人童年真的没作业、没没没没负担吗？",
            textType = TextType.Button,
            maxLines = 1,
        )

        TextComponent(
            modifier = Modifier
                .padding(top = dp2)
                .constrainAs(timerRef) {
                    start.linkTo(verticalGuideLineForText)
                    top.linkTo(titleRef.bottom)
                },
            text = "20:38",
            maxLines = 1,
            textType = TextType.Caption,
            textColor = OL_LightGray3
        )

        val width = if (playState == FMPlayState.PLAYING) dp36 else dp72
        Box(
            modifier = Modifier
                .padding(end = dp(hPadding))
                .width(width)
//                .background(Color.Cyan)
                .constrainAs(actionRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            contentAlignment = Alignment.CenterEnd
        ) {
            AnimatePlayActions(
                playState,
                onClickPauseIcon = {
                    stopRotatingImage()
                    onClickPauseIcon()
                },
                onClickPlayIcon = {
                    startRotatingImage()
                    onClickPlayIcon()
                },
                onClickStopIcon = onClickStopIcon
            )
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun AnimatePlayActions(
    playState: FMPlayState,
    onClickPlayIcon: () -> Unit,
    onClickPauseIcon: () -> Unit,
    onClickStopIcon: () -> Unit
) {

    if (playState == FMPlayState.PAUSE) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_clear_24),
            contentDescription = "Stop",
            tint = OL_Gray,
            modifier = Modifier
                .size(dp20)
                .clickable {
                    onClickStopIcon()
                }
        )
    }

    val offsetX by animateDpAsState(
        targetValue = if (playState == FMPlayState.PLAYING) dp0 else -dp36,
        animationSpec = tween(durationMillis = 300)
    )

    val iconId = if (playState == FMPlayState.PLAYING)
        R.drawable.ic_baseline_pause_24
    else
        R.drawable.ic_baseline_play_arrow_24


    CompositionLocalProvider(
        LocalRippleTheme provides ClearRippleTheme
    ) {
        Box(
            modifier = Modifier
                .offset(x = offsetX)
                .size(dp28)
                .clip(CircleShape)
                .border(width = dp2, color = OL_Gray, shape = CircleShape)
                .clickable {
                    if (playState == FMPlayState.PLAYING)
                        onClickPauseIcon()
                    else
                        onClickPlayIcon()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Pause",
                tint = OL_Gray,
                modifier = Modifier
                    .size(dp16)

            )
        }
    }

}

@Preview
@Composable
fun AudioPlayComponentPreview() {
    MyOpenLanguageTheme {
        var playState by remember {
            mutableStateOf(FMPlayState.PLAYING)
        }

        if (playState != FMPlayState.STOPPED) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AudioPlayComponent(
                    playState = playState,
                    onClickPauseIcon = {
                        playState = FMPlayState.PAUSE
                    },
                    onClickPlayIcon = {
                        playState = FMPlayState.PLAYING
                    },
                    onClickStopIcon = {
                        playState = FMPlayState.STOPPED
                    }
                )
            }
        }
    }

}