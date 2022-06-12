package com.chinalwb.myopenlanguage.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.chinalwb.myopenlanguage.theme.OL_TextBlack
import com.chinalwb.myopenlanguage.util.sp12
import com.chinalwb.myopenlanguage.util.sp24

sealed class TextType {
    object Header1: TextType()
    object Header2: TextType()
    object Header3: TextType()
    object Header4: TextType()
    object Header5: TextType()
    object Button: TextType()
    object Button2: TextType()
    object Body1: TextType()
    object Body2: TextType()
    object Caption: TextType()
}


@Composable
private fun getTextTypeStyle(textType: TextType) =
    when (textType) {
        TextType.Header1 -> MaterialTheme.typography.h1
        TextType.Header2 -> MaterialTheme.typography.h2
        TextType.Header3 -> MaterialTheme.typography.h3
        TextType.Header4 -> MaterialTheme.typography.h4
        TextType.Header5 -> MaterialTheme.typography.h5
        TextType.Body1 -> MaterialTheme.typography.body1
        TextType.Body2 -> MaterialTheme.typography.body2
        TextType.Caption -> MaterialTheme.typography.caption
        TextType.Button -> MaterialTheme.typography.button
        TextType.Button2 -> MaterialTheme.typography.button.copy(fontSize = sp12)
    }


@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    textType: TextType = TextType.Header1,
    textColor: Color = OL_TextBlack,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign = TextAlign.Start,
    text: String
) {
    val style = getTextTypeStyle(textType)

    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = textColor,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign
    )
}

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    textType: TextType = TextType.Header1,
    textColor: Color = OL_TextBlack,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    text: AnnotatedString
) {
    val style = getTextTypeStyle(textType)

    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = textColor,
        maxLines = maxLines,
        overflow = overflow
    )
}
