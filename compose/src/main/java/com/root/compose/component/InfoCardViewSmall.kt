package com.root.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoCardViewSmall(
    titleText: String,
    titleFontSize: TextUnit = 16.sp,
    titleColor: Color = Color.DarkGray,
    contentText: String,
    contentFontSize: TextUnit = 14.sp,
    contentColor: Color = Color.Black,
    leftIcon: ImageVector,
    leftIconSize: Dp = 24.dp,
    leftIconTint: Color = Color.DarkGray,
    leftIconDescription: String = "InfoIcon",
    shadow: Dp = 4.dp,
    cornerRadius: Dp = 8.dp,
    contentPadding: Dp = 16.dp,
    background: Color = Color.White,
) {
    Box {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .shadow(
                    shadow,
                    RoundedCornerShape(cornerRadius)
                )
                .background(background),
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Row(modifier = Modifier.padding(contentPadding)) {
                    Icon(
                        leftIcon,
                        contentDescription = leftIconDescription,
                        modifier = Modifier
                            .size(leftIconSize)
                            .align(Alignment.CenterVertically),
                        tint = leftIconTint
                    )
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterVertically)) {
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(start = contentPadding),
                            color = titleColor,
                            fontSize = titleFontSize,
                            fontWeight = FontWeight.SemiBold,
                            text = titleText,
                        )
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(start = contentPadding),
                            color = contentColor,
                            fontSize = contentFontSize,
                            fontWeight = FontWeight.Normal,
                            text = contentText,
                        )
                    }
                }
            }
        }
    }
}
