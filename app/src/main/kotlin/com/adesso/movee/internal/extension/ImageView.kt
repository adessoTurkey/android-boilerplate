package com.adesso.movee.internal.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?) = Glide.with(this)
    .load(url)
    .into(this)

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholderRes: Int?,
    @DrawableRes errorRes: Int?
) {
    val requestOptions = RequestOptions().apply {
        placeholderRes?.let { placeholder(it) }
        errorRes?.let { error(it) }
    }

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .into(this)
}
