package com.root.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage

@Composable
fun InfoCardView(
    titleText: String,
    titleFontSize: TextUnit = 16.sp,
    titleColor: Color = Color.DarkGray,
    contentText: String,
    contentFontSize: TextUnit = 14.sp,
    contentColor: Color = Color.Black,
    headerImageUrl: String,
    headerImageHeight: Dp = 210.dp,
    headerImageDescription: String = "HeaderImage",
    shadow: Dp = 4.dp,
    cornerRadius: Dp = 8.dp,
    contentPadding: Dp = 16.dp,
    background: Color = Color.White,
) {
    Box {
        Box {
            Box(
                modifier = Modifier
                    .background(background)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .shadow(shadow, RoundedCornerShape(cornerRadius)),
            ) {
                Card {
                    Column {
                        AsyncImage(
                            model = headerImageUrl,
                            contentDescription = headerImageDescription,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(headerImageHeight)
                        )
                        Row(modifier = Modifier.padding(contentPadding)) {
                            Column(modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically)) {
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth(),
                                    color = titleColor,
                                    fontSize = titleFontSize,
                                    fontWeight = FontWeight.SemiBold,
                                    text = titleText,
                                )
                                Text(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth(),
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
    }
}
