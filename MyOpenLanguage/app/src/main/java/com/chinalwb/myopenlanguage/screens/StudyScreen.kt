package com.chinalwb.myopenlanguage.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.chinalwb.myopenlanguage.R
import com.chinalwb.myopenlanguage.components.*
import com.chinalwb.myopenlanguage.model.CourseModel
import com.chinalwb.myopenlanguage.model.FMLessonModel
import com.chinalwb.myopenlanguage.theme.*
import com.chinalwb.myopenlanguage.util.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

const val CORNER_SIZE = 16
const val H_PADDING = 24
const val LAZY_ROW_SPACER = 12

val courses = listOf(
    CourseModel(
        OL_OnPrimaryBlue,
        com.chinalwb.myopenlanguage.R.drawable.ic_baseline_play_circle_outline_24,
        "真人外教系统课",
        "智能复习课",
        "发现本节课32个知识点的掌握度已经下降23%",
        "继续复习",
        com.chinalwb.myopenlanguage.R.drawable.bgn1
    ),
    CourseModel(
        OL_OnPrimaryGreen,
        com.chinalwb.myopenlanguage.R.drawable.ic_baseline_swap_horizontal_circle_24,
        "地道音频课",
        "Having a second child",
        "\"二孩\"在中国已经不是罕见现象，越来越多夫妇已经开始养育第二个孩子。",
        "继续学习",
        com.chinalwb.myopenlanguage.R.drawable.bg3
    )
)

val exercises = listOf(
    CourseModel(
        OL_LightGreen,
        0,
        "",
        "考研核心词汇",
        "今日待背单词",
        "单词速记",
        null,
        pinnedImageResId = R.drawable.icon_notebook,
        "80"
    ),
    CourseModel(
        OL_LightBlue,
        0,
        "",
        "每日晨读",
        "Let's go over your speech one more time.",
        "去晨读",
        null,
        pinnedImageResId = R.drawable.icon_alarm,
        ""
    )
)

val FMLessonList = listOf<FMLessonModel>(
    FMLessonModel(
        title = "最有用的英语单词之Hit"
    ),
    FMLessonModel(
        title = "掌握这些前缀，背单词事半功倍"
    ),
    FMLessonModel(
        title = "你跳刘畊宏毽子操了吗？"
    )
)

val FMRandomLessonList = listOf<FMLessonModel>(
    FMLessonModel(
        title = "今年美国最火的剧集是一部韩剧！",
        lessonImage = R.drawable.icon_fm1
    ),
    FMLessonModel(
        title = "秋天真的是最浪漫的季节！",
        lessonImage = R.drawable.icon_fm2
    ),
    FMLessonModel(
        title = "环球影城vs迪士尼，哪个更好玩？",
        lessonImage = R.drawable.icon_fm3
    ),
    FMLessonModel(
        title = "英语形容词排序，黄金法则在这里",
        lessonImage = R.drawable.icon_fm4
    ),
    FMLessonModel(
        title = "Jenny和Adam大力推荐的八大英语金句",
        lessonImage = R.drawable.icon_fm5
    )
)

@Composable
fun FMBar(modifier: Modifier, playState: FMPlayState, onFMClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .padding(horizontal = dp(H_PADDING))
            .height(dp48)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(dp32)
                .clip(RoundedCornerShape(dp16))
                .background(OL_LightGray4)
        ) {
            TextComponent(
                modifier = Modifier
                    .padding(start = dp48, end = dp16, top = dp4, bottom = dp4),
                text = "FM >",
                textType = TextType.Button
            )
        }

        FMPlay(fmPlayState = playState) {
            onFMClick()
        }
    }
}

@Preview
@Composable
fun FMPlayPreview() {

    Box(modifier = Modifier.size(200.dp)) {
        FMPlay(
            fmPlayState = FMPlayState.STOPPED
        ) {

        }
    }

}

@Composable
private fun FMPlay(
    modifier: Modifier = Modifier,
    fmPlayState: FMPlayState,
    onClick: () -> Unit
) {
    val composeRotateAnimation: ComposeRotateAnimation =
        if (fmPlayState == FMPlayState.PLAYING) {
            useRotateAnimation(key = fmPlayState)
        } else {
            ComposeRotateAnimation(0f, dp18, false)
        }

    val sizeDp = animateDpAsState(
        targetValue = if (composeRotateAnimation.isFinished) {
            dp12
        } else {
            composeRotateAnimation.size
        },
        animationSpec = if (composeRotateAnimation.isFinished) {
            keyframes {
                durationMillis = 300
                dp24 at 200
            }
        } else {
            tween(1)
        }
    )

    var playIcon = R.drawable.ic_baseline_play_arrow_24
    if (composeRotateAnimation.isFinished) {
        playIcon = if (fmPlayState == FMPlayState.PLAYING) {
            R.drawable.ic_baseline_circle_24
        } else {
            R.drawable.ic_baseline_play_arrow_24
        }
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .size(dp40)
            .border(dp2, OL_LightGray4, CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        OL_BLACK_1,
                        OL_BLACK_2,
                        OL_BLACK_1
                    )
                )
            )
            .clickable {
                onClick()
            }
    ) {
        // val composeRotateAnimation = useRotateAnimation(fmPlayState)
        Icon(
            modifier = Modifier
                .graphicsLayer(
                    rotationZ = composeRotateAnimation.rotationAngle
                )
                .size(sizeDp.value),
            painter = painterResource(id = playIcon),
            contentDescription = "Play",
            tint = OL_GOLDEN
        )
    }
}

data class ComposeRotateAnimation(
    val rotationAngle: Float,
    val size: Dp,
    val isFinished: Boolean
)

enum class RotateAnimationState {
    Started,
    Ended
}

enum class FMPlayState {
    STOPPED,
    PLAYING,
    PAUSE
}

enum class ScrollDirection {
    UP,
    DOWN,
    NONE
}

@Composable
fun useRotateAnimation(key: Any): ComposeRotateAnimation {
    val state = remember(key) {
        MutableTransitionState(RotateAnimationState.Started)
    }

    state.targetState = RotateAnimationState.Ended

    val transition = updateTransition(state, label = "Rotate animation")

    val rotationAngle by transition.animateFloat(
        label = "Rotate angle",
        transitionSpec = {
            // spring(dampingRatio = Spring.DampingRatioLowBouncy)
            tween(durationMillis = 600)
        }
    ) { rotateAnimationState ->
        when (rotateAnimationState) {
            RotateAnimationState.Started -> 0f
            RotateAnimationState.Ended -> 495f
        }
    }

    val sizeDp by transition.animateDp(
        label = "Size anim",
        transitionSpec = {
            tween(durationMillis = 600)
        }
    ) { rotateAnimationState ->
        when (rotateAnimationState) {
            RotateAnimationState.Started -> dp18
            RotateAnimationState.Ended -> dp4
        }
    }

    val isFinished = transition.currentState == transition.targetState
    return ComposeRotateAnimation(
        rotationAngle,
        sizeDp,
        isFinished
    )
}

@Composable
fun WelcomeMessage(
    modifier: Modifier = Modifier,
    text: String,
    textType: TextType = TextType.Header3,
    textAtRight: String = "",
    textAtRightColor: Color = OL_LightGray,
    textAtRightType: TextType = TextType.Caption,
    showDivider: Boolean = false,
    textAtRightOnClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (startRef, centerRef, endRef) = createRefs()
        TextComponent(
            modifier = Modifier.constrainAs(startRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            text = text,
            textType = textType
        )

        if (showDivider) {
            Divider(
                modifier = Modifier.constrainAs(centerRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                color = Color.Red, thickness = dp1
            )
        }

        TextComponent(
            modifier = Modifier.constrainAs(endRef) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            text = textAtRight,
            textColor = textAtRightColor,
            textType = textAtRightType
        )
    }

}

@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    upperNumbers: List<Int>,
    unitText: String,
    lowerText: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val numberStyle = MaterialTheme.typography.h2.toSpanStyle().copy(
            color = OL_TextWhite
        )
        val unitStyle = MaterialTheme.typography.body1.toSpanStyle().copy(
            color = OL_TextWhite
        )

        var baselinePadding by remember {
            mutableStateOf(dp0)
        }

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            RollerNumberAnimator(
                startValue = upperNumbers[0],
                endValue = upperNumbers[1],
                vPadding = 0f,
                textSize = sp28,
                backgroundColor = Color.Transparent,
                onLayoutFinished = {
                    baselinePadding = it
                }
            )

            val upperTextTotal = buildAnnotatedString {
                withStyle(unitStyle) {
                    append(" ")
                    append(unitText)
                }
            }

            TextComponent(
                modifier = Modifier
                    .paddingFromBaseline(bottom = baselinePadding),
                text = upperTextTotal
            )
        }

        Spacer(modifier = Modifier.height(dp4))
        TextComponent(
            text = lowerText,
            textType = TextType.Body1,
            textColor = OL_TextWhite
        )
    }
}

@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    upperText: String,
    unitText: String,
    lowerText: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val numberStyle = MaterialTheme.typography.h2.toSpanStyle().copy(
            color = OL_TextWhite
        )
        val unitStyle = MaterialTheme.typography.body1.toSpanStyle().copy(
            color = OL_TextWhite
        )

        val divider = " "
        val upperTextTotal = buildAnnotatedString {
            withStyle(numberStyle) {
                append(upperText)
            }

            withStyle(unitStyle) {
                append(divider)
                append(unitText)
            }
        }

        TextComponent(
            text = upperTextTotal
        )
        Spacer(modifier = Modifier.height(dp4))
        TextComponent(
            text = lowerText,
            textType = TextType.Body1,
            textColor = OL_TextWhite
        )
    }
}


@Composable
fun Pinned(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    text: String
) {
    val shape = RoundedCornerShape(
        topStart = dp0,
        topEnd = dp0,
        bottomEnd = dp12,
        bottomStart = dp12
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
    ) {
        TextComponent(
            modifier = Modifier.padding(
                start = dp8,
                end = dp8,
                top = dp4,
                bottom = dp4
            ),
            textType = TextType.Caption,
            textColor = textColor,
            text = text
        )
    }

}

@Composable
fun CheckInBoard(
    modifier: Modifier = Modifier,
) {

    ConstraintLayout(modifier = modifier) {
        val shape = RoundedCornerShape(corner = CornerSize(dp(CORNER_SIZE)))
        val (pinnedRef, cardRef) = createRefs()

        Box(
            modifier = Modifier
                .padding(top = dp2)
                .clip(shape)
                .background(color = OL_OnPrimaryGreen)
                .constrainAs(cardRef) {
                    top.linkTo(parent.top)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(top = dp(32), bottom = dp(H_PADDING), start = dp8, end = dp8)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusCard(
                    upperNumbers = listOf(0, 36),
                    unitText = "/ 10 分钟",
                    lowerText = "今日已学"
                )
                StatusCard(upperText = "247.2", unitText = "小时", lowerText = "累计学习")
                StatusCard(
                    upperNumbers = listOf(402, 419),
                    unitText = "天",
                    lowerText = "累计打卡"
                )
            }
        }

        Pinned(
            modifier = Modifier
                .padding(
                    top = dp0,
                    start = dp32
                )
                .constrainAs(pinnedRef) {
                    top.linkTo(parent.top)
                },
            backgroundColor = OL_PinnedGreen,
            textColor = OL_TextWhite,
            text = "学习打卡 > "
        )
    }

}

@Composable
fun CourseProfile(
    modifier: Modifier = Modifier,
    courseModel: CourseModel
) {

    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val width = screenWidthDp - H_PADDING * 2 - LAZY_ROW_SPACER
    val shape = RoundedCornerShape(dp(CORNER_SIZE))

    ConstraintLayout(
        modifier = modifier
            .width(dp(width))
            .height(dp((width * .85f).toInt()))
            .clip(shape)
            .background(courseModel.backgroundColor)
    ) {
        val (
            actionRef,
            courseNameRef,
            viewCourseActionRef,
            lessonTitleRef,
            lessonDescRef
        ) = createRefs()

        val topPadding = dp16
        IconAndText(
            modifier = Modifier
                .padding(start = dp(H_PADDING), top = topPadding)
                .constrainAs(courseNameRef) {
                    top.linkTo(parent.top)
                },
            text = courseModel.courseName,
            textColor = OL_TextWhite,
            icon = ImageVector.vectorResource(id = courseModel.courseIconResId),
            iconColor = OL_TextWhite
        )

        RoundButtonComponent(
            modifier = Modifier
                .padding(end = dp(H_PADDING), top = topPadding)
                .constrainAs(viewCourseActionRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            backgroundColor = OL_Neutral3,
            cornerSize = dp16,
            contentPadding = PaddingValues(horizontal = dp8, vertical = dp4),
            text = "查看课程 > ",
            textColor = OL_TextWhite
        )


        val cornerSize = dp24
        val buttonShape = RoundedCornerShape(cornerSize)
        val buttonHeight = cornerSize * 2
        Button(
            modifier = Modifier
                .padding(start = dp(H_PADDING), end = dp(H_PADDING), bottom = dp(16))
                .fillMaxWidth()
                .height(buttonHeight)
                .clip(buttonShape)
                .background(OL_PrimaryWhite)
                .constrainAs(actionRef) {
                    bottom.linkTo(parent.bottom)
                },
            onClick = { }
        ) {
            TextComponent(
                text = courseModel.actionText,
                textType = TextType.Button,
                textColor = OL_TextBlack
            )
        }


        val lessonDescWidth = ((screenWidthDp - H_PADDING * 2) * 0.6f).toInt()
        TextComponent(
            modifier = Modifier
                .padding(start = dp(H_PADDING), bottom = dp16)
                .width(dp(lessonDescWidth))
                .constrainAs(lessonDescRef) {
                    bottom.linkTo(actionRef.top)
                },
            text = courseModel.lessonDesc,
            textType = TextType.Body1,
            textColor = OL_TextWhite,
            maxLines = 2
        )

        TextComponent(
            modifier = Modifier
                .padding(start = dp(H_PADDING), bottom = dp8)
                .width(dp(lessonDescWidth))
                .constrainAs(lessonTitleRef) {
                    bottom.linkTo(lessonDescRef.top)
                },
            text = courseModel.lessonTitle,
            textType = TextType.Header3,
            textColor = OL_TextWhite
        )
    }
}

@Composable
fun MyCourses(
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier) {
        WelcomeMessage(text = "我的课程")

        LazyRow(
            modifier = Modifier.padding(top = dp12),
            content = {
                itemsIndexed(
                    items = courses,
                    itemContent = { index, courseModel ->
                        CourseProfile(
                            courseModel = courseModel
                        )
                        SpacerWidth(width = dp(LAZY_ROW_SPACER))
                    }
                )
            }
        )
    }
}

@Composable
fun ExerciseProfile(
    modifier: Modifier = Modifier,
    courseModel: CourseModel
) {
    val shape = RoundedCornerShape(dp(CORNER_SIZE))

    ConstraintLayout(modifier = modifier) {

        ConstraintLayout(
            modifier = Modifier
                .padding(top = dp16)
                .height(dp(216))
                .clip(shape)
                .background(courseModel.backgroundColor)
        ) {

            val (
                actionRef,
                subtitleRef,
                courseNameRef,
                viewCourseActionRef,
                lessonTitleRef,
                lessonDescRef
            ) = createRefs()

            val cornerSize = dp16
            val buttonShape = RoundedCornerShape(cornerSize)
            val buttonHeight = cornerSize * 2
            RoundButtonComponent(
                modifier = Modifier
                    .padding(start = dp(H_PADDING), end = dp(H_PADDING), bottom = dp(16))
                    .fillMaxWidth()
                    .height(buttonHeight)
                    .clip(buttonShape)
                    .constrainAs(actionRef) {
                        bottom.linkTo(parent.bottom)
                    },
                backgroundColor = OL_PrimaryWhite,
                cornerSize = cornerSize,
                text = courseModel.actionText,
                textType = TextType.Button2,
                textColor = OL_TextBlack,
                onClick = { }
            )

            TextComponent(
                modifier = Modifier
                    .padding(start = dp(H_PADDING), bottom = dp8)
                    .constrainAs(subtitleRef) {
                        bottom.linkTo(actionRef.top)
                    },
                text = courseModel.lessonDescSubtitle ?: "",
                textType = TextType.Header2
            )

//            val anchor =
//                if (courseModel.lessonDescSubtitle.isNullOrEmpty())
//                    actionRef.top
//                else
//                    subtitleRef.top

            val horizontalGuideLine = createGuidelineFromTop(0.3f)
            TextComponent(
                modifier = Modifier
                    .padding(start = dp(H_PADDING), top = dp4, end = dp(H_PADDING), bottom = dp4)
                    .constrainAs(lessonTitleRef) {
                        top.linkTo(horizontalGuideLine)
                    },
                text = courseModel.lessonTitle,
                textType = TextType.Header4
            )

            TextComponent(
                modifier = Modifier
                    .padding(start = dp(H_PADDING), end = dp(H_PADDING), bottom = dp0)
                    .constrainAs(lessonDescRef) {
                        top.linkTo(lessonTitleRef.bottom)
                    },
                text = courseModel.lessonDesc,
                textType = TextType.Caption,
                maxLines = 2
            )
        }

        Image(
            modifier = Modifier
                .padding(start = dp(H_PADDING))
                .size(dp64),
            painter = painterResource(
                id = courseModel.pinnedImageResId ?: R.drawable.icon_notebook
            ),
            contentDescription = ""
        )
    }

}

@Composable
fun DailyExercise(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        WelcomeMessage(text = "每日练习")

        val cardModifier = Modifier
            .weight(1f)
            .padding(top = dp12)

        Row(modifier = Modifier) {
            // Text(text = "Hello", Modifier.weight(1f).background(exercises[0].backgroundColor))
            // Text(text = "World", Modifier.weight(1f).background(exercises[1].backgroundColor))
            ExerciseProfile(
                modifier = cardModifier,
                courseModel = exercises[0]
            )
            SpacerWidth(width = dp16)
            ExerciseProfile(
                modifier = cardModifier,
                courseModel = exercises[1]
            )
        }
    }
}

@Composable
fun FMSuggestionSimpleProfile(
    modifier: Modifier = Modifier,
    fmLessonModel: FMLessonModel
) {
    Box(
        modifier = modifier
            .padding(horizontal = dp(H_PADDING))
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconAndText(
            icon = ImageVector.vectorResource(id = fmLessonModel.icon),
            iconSize = dp28,
            iconColor = fmLessonModel.iconColor,
            text = fmLessonModel.title,
            textColor = OL_TextBlack,
            textType = TextType.Body1,
            spacerWidth = dp8
        )
    }
}

@Composable
fun FMSuggestionList(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = dp8)
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier.height(dp(120)),
            content = {
                items(
                    items = FMLessonList,
                    itemContent = { item ->
                        FMSuggestionSimpleProfile(
                            modifier = Modifier.height(dp(40)),
                            fmLessonModel = item
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun FMSuggestionCardProfile(
    modifier: Modifier = Modifier,
    fmLessonModel: FMLessonModel
) {

    Box(
        modifier = modifier,
    ) {
        val screenWidthDp = LocalConfiguration.current.screenWidthDp
        val width = screenWidthDp - H_PADDING * 5
        val shape = RoundedCornerShape(dp(CORNER_SIZE))

        val cardHeight = 56

        ConstraintLayout(
            modifier = Modifier
                .width(dp(width))
                .height(dp(cardHeight))
                .clip(shape)
                .background(OL_LightGray2)
        ) {
            val (imageRef, iconRef, titleRef) = createRefs()

            Image(
                modifier = Modifier
                    .size(dp(cardHeight))
                    .clip(shape)
                    .constrainAs(imageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(id = fmLessonModel.lessonImage),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Icon(
                modifier = Modifier
                    .size(dp24)
                    .clip(CircleShape)
                    .background(OL_Neutral6)
                    .constrainAs(iconRef) {
                        start.linkTo(imageRef.start)
                        top.linkTo(imageRef.top)
                        end.linkTo(imageRef.end)
                        bottom.linkTo(imageRef.bottom)
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_play_circle_outline_24),
                contentDescription = "",
                tint = OL_PrimaryWhite
            )

            val textWidth = width - cardHeight
            TextComponent(
                modifier = Modifier
                    .width(dp(textWidth))
                    .padding(start = dp8, end = dp8)
                    .constrainAs(titleRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(imageRef.end)
                    },
                text = fmLessonModel.title,
                maxLines = 1,
                textType = TextType.Body1,
                textColor = OL_DarkGray
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FMRandomList(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = FMRandomLessonList.size)

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {

        HorizontalPager(
            state = pagerState,
            horizontalAlignment = Alignment.Start,
            itemSpacing = dp0,
            modifier = Modifier.fillMaxWidth()
        ) { page: Int ->

//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .padding(start = dp(H_PADDING))
//                    .offset {
//                        val pageOffset = calculateCurrentOffsetForPage(page)
//                        val offsetToFillStartPadding = minOf(page + pageOffset, 0f)
//                        val offsetToFillEndPadding = maxOf(page + pageOffset - FMRandomLessonList.size + 2, 0f)
//                        val xOffset = dp(H_PADDING).toPx() * (offsetToFillStartPadding + offsetToFillEndPadding)
//                        Log.w("xx", "----")
//                        Log.w("xx", "page = $page, pageOffset = $pageOffset, xoffset = $xOffset")
//                        IntOffset(x = xOffset.roundToInt(), y = 0)
//                    }
//                    .width(dp(width))
//                    .height(dp(56))
//                    .clip(RoundedCornerShape(dp(CORNER_SIZE)))
//                    .border(dp1, Color.Red, shape = RoundedCornerShape(dp(CORNER_SIZE)))
//                    .background(Color.Cyan)
//            ) {
//                Text(text = "Page $page")
//            }
            FMSuggestionCardProfile(
                modifier = Modifier
                    .padding(start = dp(H_PADDING))
                    .offset {
                        val pageOffset = calculateCurrentOffsetForPage(page)
                        val offsetToFillStartPadding = minOf(page + pageOffset, 0f)
                        val offsetToFillEndPadding =
                            maxOf(page + pageOffset - FMRandomLessonList.size + 2, 0f)
                        val xOffset =
                            dp(H_PADDING).toPx() * (offsetToFillStartPadding + offsetToFillEndPadding)
                        IntOffset(x = xOffset.roundToInt(), y = 0)
                    },
                fmLessonModel = FMRandomLessonList[page]
            )
        }
//        LazyRow (
//            modifier = Modifier,
//            content = {
//                itemsIndexed(
//                    items = FMRandomLessonList,
//                    itemContent = { index, item ->
//                        FMSuggestionCardProfile(
//                            modifier = Modifier,
//                            fmLessonModel = item
//                        )
//                        if (index == FMRandomLessonList.lastIndex) {
//                            SpacerWidth(width = dp(H_PADDING))
//                        }
//                    }
//                )
//            }
//        )
    }
}

@Composable
fun OpenFM(
    modifier: Modifier = Modifier
) {

    ConstraintLayout(modifier = modifier) {
        val (topRef, carouselRef, headlineRef, teacherRef, suggestionsRef) = createRefs()

        WelcomeMessage(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topRef) {
                    top.linkTo(parent.top)
                },
            text = "开言FM",
            textAtRight = "发现更多 >",
            textAtRightType = TextType.Body1,
            textAtRightColor = OL_Gray
        )

        TextCarousel(
            modifier = Modifier
                .padding(
                    start = dp(80),
                    top = dp(4)
                )
                .constrainAs(carouselRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            textList = scenarioList,
            carouselHeight = 20.dp,
            clipShape = RoundedCornerShape(4.dp),
            textPadding = 8.dp,
            textSize = 10.sp
        )

        Card(
            modifier = Modifier
                .padding(top = dp24)
                .fillMaxWidth()
                .constrainAs(headlineRef) {
                    top.linkTo(topRef.bottom)
                },
            shape = RoundedCornerShape(dp(CORNER_SIZE)),
            backgroundColor = OL_OnPrimaryGreen
        ) {
            val paddingModifier = Modifier
                .padding(start = dp(H_PADDING), end = dp(H_PADDING))

            Column {
                TextComponent(
                    modifier = paddingModifier.padding(top = dp24, bottom = dp4),
                    text = "潘吉Jenny告诉你",
                    textType = TextType.Header4,
                    textColor = OL_TextWhite
                )

                TextComponent(
                    modifier = paddingModifier.padding(top = dp4, bottom = dp8),
                    text = "聊文化学英语，连续7年苹果最佳播客",
                    textType = TextType.Caption,
                    textColor = OL_TextWhite
                )

                SpacerHeight(height = dp(96))
            }
        }

        Image(
            modifier = Modifier
                .offset(x = dp24, y = dp0)
                .size(dp(160))
                .constrainAs(teacherRef) {
                    top.linkTo(topRef.bottom)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.icon_teacher),
            contentDescription = ""
        )

        Card(
            modifier = Modifier
                .padding(bottom = dp16)
                .offset(y = -dp48)
                .fillMaxWidth()
                .constrainAs(suggestionsRef) {
                    top.linkTo(teacherRef.bottom)
                },
            shape = RoundedCornerShape(dp(CORNER_SIZE)),
            backgroundColor = OL_PrimaryWhite,
            elevation = 8.dp
        ) {
            Column {
                WelcomeMessage(
                    modifier = Modifier.padding(
                        start = dp(H_PADDING),
                        top = dp24
                    ),
                    text = "推荐你听",
                    textType = TextType.Button
                )

                FMSuggestionList()

                WelcomeMessage(
                    modifier = Modifier.padding(
                        start = dp(H_PADDING),
                        top = dp24
                    ),
                    text = "随心听",
                    textType = TextType.Button
                )

                FMRandomList(
                    modifier = Modifier.padding(top = dp12, bottom = dp24)
                )
            }
        }
    }

}


@Composable
fun Footer(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextComponent(
            text = "——  世界很大，尽管开言  ——",
            textType = TextType.Body1,
            textColor = OL_LightGray3
        )
    }

}

@Composable
fun StudyScreen(
    modifier: Modifier = Modifier,
) {
    var playState by remember {
        mutableStateOf(FMPlayState.STOPPED)
    }
    var scrollDirection by remember {
        mutableStateOf(ScrollDirection.NONE)
    }
    var fmForceShow by remember {
        mutableStateOf(false)
    }
    var fmImageRotationZ by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = modifier.fillMaxHeight()
    ) {
        val (fmBarRef, contentRef, playerRef) = createRefs()
        FMBar(modifier = Modifier.constrainAs(fmBarRef) {
            top.linkTo(parent.top)
        }, playState = playState) {
            fmForceShow = true
            playState = when (playState) {
                FMPlayState.STOPPED -> {
                    FMPlayState.PLAYING
                }
                FMPlayState.PLAYING -> {
                    FMPlayState.PAUSE
                }
                FMPlayState.PAUSE -> {
                    FMPlayState.PLAYING
                }
            }
        }

        var prevScrollPos by remember {
            mutableStateOf(0)
        }
        val scrollState = rememberScrollState()
        if (scrollState.isScrollInProgress) {
            if (scrollState.value > prevScrollPos) {
                scrollDirection = ScrollDirection.DOWN
            } else if (scrollState.value < prevScrollPos) {
                scrollDirection = ScrollDirection.UP
            }

            SideEffect {
                scope.launch {
                    if (fmForceShow) {
                        delay(100)
                        fmForceShow = false
                    }
                }
            }
        }

        prevScrollPos = scrollState.value

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(contentRef) {
                    top.linkTo(fmBarRef.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(scrollState)
        ) {
            SpacerHeight(height = dp16)
            WelcomeMessage(
                modifier = Modifier.padding(start = dp(H_PADDING), end = dp(H_PADDING)),
                text = "Hello! 134****0502"
            )
            SpacerHeight(height = dp12)
            CheckInBoard(modifier = Modifier.padding(start = dp(H_PADDING), end = dp(H_PADDING)))
            SpacerHeight(height = dp24)
            MyCourses(modifier = Modifier.padding(start = dp(H_PADDING)))
            SpacerHeight(height = dp24)
            DailyExercise(modifier = Modifier.padding(horizontal = dp(H_PADDING)))
            SpacerHeight(height = dp24)
            OpenFM(modifier = Modifier.padding(horizontal = dp(H_PADDING)))
            Footer()
            SpacerHeight(height = dp24)
        }


        val fmPlayerVisibility =
            fmForceShow
                    || (playState != FMPlayState.STOPPED
                    && scrollDirection != ScrollDirection.DOWN
                    )
        AnimatedVisibility(
            modifier = Modifier.constrainAs(playerRef) {
                bottom.linkTo(parent.bottom)
            },
            visible = fmPlayerVisibility,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically(
                // animationSpec = tween(durationMillis = 3000)
            ) { it } + fadeOut()
        ) {
            AudioPlayComponent(
                modifier = Modifier,
                playState = playState,
                fmImageRotationZ = fmImageRotationZ,
                onClickPauseIcon = {
                    playState = FMPlayState.PAUSE
                },
                onClickPlayIcon = {
                    playState = FMPlayState.PLAYING
                },
                onClickStopIcon = {
                    fmForceShow = false
                    fmImageRotationZ = 0f
                    playState = FMPlayState.STOPPED
                },
                onRotationUpdated = {
                    fmImageRotationZ = it
                }
            )
        }
    }
}

@Preview
@Composable
fun StudyScreenPreview() {
    MyOpenLanguageTheme {
        StudyScreen()
    }
}

@Preview
@Composable
fun StatusCardPreview() {
    MyOpenLanguageTheme {
        CheckInBoard()
    }
}

@Preview
@Composable
fun CourseProfilePreview() {
    MyOpenLanguageTheme {
        CourseProfile(
            modifier = Modifier,
            courseModel = CourseModel(
                OL_LightGray,
                com.chinalwb.myopenlanguage.R.drawable.ic_baseline_play_circle_outline_24,
                "真人外教系统课",
                "智能复习课",
                "发现本节课32个知识点的掌握度已经下降23%",
                "继续复习",
                com.chinalwb.myopenlanguage.R.drawable.bgone
            )
        )
    }
}

@Composable
fun BoxAnimation() {
    var state by remember {
        mutableStateOf(0)
    }
    val sizeDp = animateDpAsState(
        targetValue = if (state == 0) 40.dp else 20.dp,
        animationSpec = if (state == 0) {
            tween(1000)
        } else {
            keyframes {
                durationMillis = 1000
                10.dp at 500
                30.dp at 750
                20.dp at 1000
            }
        }
    )
//    val bgColor = animateColorAsState(targetValue = )
//    if (state == 1) {
//
//    }
    Box(modifier = Modifier
        .size(sizeDp.value)
        .background(Color.Cyan)
        .clickable {
            state = if (state == 0)
                1
            else
                0
        }
    ) {

    }
}

@Preview
@Composable
fun BoxAnimationPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        BoxAnimation()
    }
}