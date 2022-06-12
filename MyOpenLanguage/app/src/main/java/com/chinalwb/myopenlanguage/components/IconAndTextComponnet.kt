package com.chinalwb.myopenlanguage.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.chinalwb.myopenlanguage.util.dp24
import com.chinalwb.myopenlanguage.util.dp4

@Composable
fun IconAndText(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = dp24,
    iconDesc: String? = null,
    iconColor: Color,
    spacerWidth: Dp = dp4,
    text: String,
    textColor: Color,
    textType: TextType = TextType.Body1,
    onClick: (() -> Unit) = {}
) {
//    CompositionLocalProvider(
//        LocalMinimumTouchTargetEnforcement provides false,
//    ) {
//        TextButton(
//            modifier = modifier,
//            contentPadding = PaddingValues(dp0),
//            onClick = onClick
//        ) {
//
//        }
//    }

    Row(modifier = modifier.clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically) {
        Icon(modifier = Modifier.size(iconSize), imageVector = icon, contentDescription = iconDesc, tint = iconColor)
        SpacerWidth(width = spacerWidth)
        TextComponent(text = text, textType = textType, textColor = textColor)
    }


}