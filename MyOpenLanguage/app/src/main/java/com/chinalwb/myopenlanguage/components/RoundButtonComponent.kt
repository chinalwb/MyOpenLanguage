package com.chinalwb.myopenlanguage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.chinalwb.myopenlanguage.theme.MyOpenLanguageTheme
import com.chinalwb.myopenlanguage.theme.OL_OnPrimaryGreen
import com.chinalwb.myopenlanguage.theme.OL_TextWhite
import com.chinalwb.myopenlanguage.util.dp0
import com.chinalwb.myopenlanguage.util.dp4
import com.chinalwb.myopenlanguage.util.dp8

@Composable
fun RoundButtonComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    cornerSize: Dp,
    contentPadding: PaddingValues = PaddingValues(dp0),
    text: String,
    textType: TextType = TextType.Caption,
    textColor: Color,
    onClick: () -> Unit = {}
) {
    val roundShape = RoundedCornerShape(cornerSize)
    Box(
        modifier = modifier
            .clip(roundShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
    ) {
        TextComponent(
            modifier = Modifier.padding(contentPadding).align(Alignment.Center),
            text = text,
            textType = textType,
            textColor = textColor
        )
    }
}

@Preview
@Composable
fun RoundButtonPreview() {
    MyOpenLanguageTheme {
        RoundButtonComponent(
            backgroundColor = OL_OnPrimaryGreen,
            contentPadding = PaddingValues(horizontal = dp8, vertical = dp4),
            cornerSize = dp8,
            text = "查看课程",
            textColor = OL_TextWhite
        )
    }
}