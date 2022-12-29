package com.root.compose.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.root.compose.data.BottomNavigationViewWithLabelItem

@Composable
fun BottomNavigationViewWithLabel (
    itemList: List<BottomNavigationViewWithLabelItem>?,
    selectedItem: (BottomNavigationViewWithLabelItem?) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(content = {
        itemList?.forEachIndexed { index, item ->
            BottomNavigationItem(
                label = { Text(
                    item.label,
                    maxLines = 1
                ) },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.tag)
                },
                onClick = {
                    selectedIndex.value = index
                    selectedItem.invoke(item)
                },
                selected = selectedIndex.value == index,
            )
        }
    })
}
