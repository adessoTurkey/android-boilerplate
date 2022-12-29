package com.root.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
interface TagColors {
    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

@Immutable
private class DefaultTagColors(
    private val backgroundColor: Color,
    private val contentColor: Color
) : TagColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = backgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(newValue = contentColor)
    }
}

object TagDefaults {
    @Composable
    fun tagColors(
        backgroundColor: Color = Color.LightGray.copy(alpha = .4f),
        contentColor: Color = Color.DarkGray,
    ): TagColors = DefaultTagColors(backgroundColor = backgroundColor, contentColor = contentColor)
}

@Composable
fun TopicTagView(
    text: String,
    modifier: Modifier = Modifier,
    colors: TagColors = TagDefaults.tagColors(),
    contentPadding: Dp = 12.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    style: TextStyle = typography.body2.copy(fontWeight = FontWeight.Bold),
    onClick: (String?) -> Unit = {}
) {
    val tagModifier = modifier
        .clickable(onClick = {
            onClick.invoke(text)
        })
        .clip(shape = shape)
        .background(colors.backgroundColor(enabled = true).value)
        .padding(contentPadding)
    Text(
        text = text,
        color = colors.contentColor(enabled = true).value,
        modifier = tagModifier,
        style = style
    )
}
