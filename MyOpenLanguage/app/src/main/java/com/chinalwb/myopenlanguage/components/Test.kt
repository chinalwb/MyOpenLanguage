package com.chinalwb.myopenlanguage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Float.max

@Composable
fun A() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth()
    ) {
        var maxBaseline by remember { mutableStateOf(0f) }
        fun updateMaxBaseline(textLayoutResult: TextLayoutResult) {
            maxBaseline = max(maxBaseline, textLayoutResult.size.height - textLayoutResult.lastBaseline)
        }
        val topBaselinePadding = with(LocalDensity.current) { maxBaseline.toDp() }
        Text(
            text = "One \n Hello",
            modifier = Modifier.paddingFromBaseline(bottom = topBaselinePadding),
            fontSize = 20.sp,
            onTextLayout = ::updateMaxBaseline
        )
        Image(
            painter = painterResource(id = com.chinalwb.myopenlanguage.R.drawable.icon_fm1),
            contentDescription = "",
            modifier = Modifier
                .padding(bottom = topBaselinePadding)
                .size(24.dp)
        )
        Text(
            text = "two",
            modifier = Modifier.paddingFromBaseline(bottom = topBaselinePadding),
            fontSize = 40.sp,
            onTextLayout = ::updateMaxBaseline
        )
    }
}

@Composable
fun TestComponent() {
    Row(Modifier.fillMaxHeight()) {
        // The center of the magenta Box and the baselines of the two texts will be
        // vertically aligned. Note that alignBy() or alignByBaseline() has to be specified
        // for all children we want to take part in the alignment. For example, alignByBaseline()
        // means that the baseline of the text should be aligned with the alignment line
        // (possibly another baseline) specified for siblings using alignBy or alignByBaseline.
        // If no other sibling had alignBy() or alignByBaseline(), the modifier would have no
        // effect.
        Box(
            modifier = Modifier
                .size(80.dp, 40.dp)
                .alignBy { it.measuredHeight / 1 }
                .background(Color.Magenta)
        )
        Text(
            text = "Text 1",
            fontSize = 40.sp,
            modifier = Modifier

                .alignByBaseline()
                .background(color = Color.Red)
        )
        Text(
            text = "Text 2",
            modifier = Modifier
                .alignByBaseline()
                .background(color = Color.Cyan)
        )
    }
}

@Preview
@Composable
fun TestPreview() {
    Box(modifier = Modifier) {
        A()
    }
}