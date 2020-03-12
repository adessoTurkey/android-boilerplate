package com.adesso.movee.internal.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridLayoutSpaceItemDecoration(@Px private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val halfOfSpace: Int = space.div(2)

        outRect.bottom = space

        if (parent.getChildLayoutPosition(view) % spanCount == 0) {
            outRect.right = halfOfSpace
        } else {
            outRect.left = halfOfSpace
        }
    }
}
