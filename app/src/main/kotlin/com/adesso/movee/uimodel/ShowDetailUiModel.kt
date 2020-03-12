package com.adesso.movee.uimodel

import android.content.Context
import com.adesso.movee.R

interface ShowDetailUiModel : ShowUiModel {
    val runtime: Int?

    fun runtime(context: Context): String {
        return runtime?.let { runtime ->
            context.getString(R.string.show_detail_message_min_formatted, runtime)
        } ?: context.getString(R.string.show_detail_message_not_specified)
    }
}
