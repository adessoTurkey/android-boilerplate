package com.adesso.movee.internal.databinding

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adesso.movee.R
import com.adesso.movee.base.ListAdapterItem
import com.adesso.movee.internal.extension.loadImage

@BindingAdapter("hideIfNull")
fun setVisible(view: View, obj: Any?) {
    view.visibility = if (obj == null) {
        View.GONE
    } else if (obj is String && obj.isBlank()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, shouldVisible: Boolean) {
    view.isVisible = shouldVisible
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(view: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = view.adapter as? ListAdapter<ListAdapterItem, *>
    adapter?.submitList(list)
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: ListAdapter<ListAdapterItem, *>?) {
    if (adapter != null) {
        view.adapter = adapter
    }
}

@BindingAdapter("imageFromUrl", "placeholderRes", "errorRes", requireAll = false)
fun setImage(
    view: ImageView,
    url: String?,
    @DrawableRes placeholderRes: Int?,
    @DrawableRes errorRes: Int?
) {
    val defaultDrawable = R.drawable.ic_movee_placeholder

    view.loadImage(
        url,
        placeholderRes ?: defaultDrawable,
        errorRes ?: defaultDrawable
    )
}
