package com.root.compose.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.root.compose.data.BottomNavigationViewItem

@Composable
fun BottomNavigationView (
    itemList: List<BottomNavigationViewItem>?,
    selectedItem: (BottomNavigationViewItem?) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(content = {
        itemList?.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.tag)
                },
                onClick = {
                    selectedIndex.value = index
                    selectedItem.invoke(item)
                },
                selected = selectedIndex.value == index,
                alwaysShowLabel = false
            )
        }
    })
}
