package com.root.compose.data

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationViewItem(
    val icon: ImageVector,
    val tag: String,
)

data class BottomNavigationViewWithLabelItem(
    val icon: ImageVector,
    val label: String,
    val tag: String,
)
