package com.adesso.movee.uimodel

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.adesso.movee.R
import com.adesso.movee.base.ListAdapterItem
import java.io.Serializable

sealed class MultiSearchUiModel : ListAdapterItem, Serializable {

    abstract fun getTitle(): String

    abstract fun getSubtitle(): String

    abstract fun getImagePath(): String?

    fun getTypeDrawable(context: Context): Drawable? {
        val typeDrawableRes = when (this) {
            is MovieMultiSearchUiModel -> R.drawable.ic_movie
            is TvShowMultiSearchUiModel -> R.drawable.ic_tv_show
            is PersonMultiSearchUiModel -> R.drawable.ic_person
        }
        return ContextCompat.getDrawable(context, typeDrawableRes)
    }

    @StringRes
    fun getTypeStringRes(): Int {
        return when (this) {
            is MovieMultiSearchUiModel -> R.string.search_message_movie
            is TvShowMultiSearchUiModel -> R.string.search_message_tv_series
            is PersonMultiSearchUiModel -> getJob()
        }
    }
}

data class MovieMultiSearchUiModel(
    override val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String?
) : MultiSearchUiModel() {

    override fun getTitle(): String = name

    override fun getSubtitle(): String = overview

    override fun getImagePath(): String? = posterPath
}

data class TvShowMultiSearchUiModel(
    override val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String?
) : MultiSearchUiModel() {

    override fun getTitle(): String = name

    override fun getSubtitle(): String = overview

    override fun getImagePath(): String? = posterPath
}

data class PersonMultiSearchUiModel(
    override val id: Long,
    val name: String,
    val knownFor: List<ProductUiModel>,
    val department: DepartmentUiModel,
    val profilePath: String?
) : MultiSearchUiModel() {

    override fun getTitle(): String = name

    override fun getSubtitle(): String = knownFor.joinToString(separator = ", ") { it.name }

    override fun getImagePath(): String? = profilePath

    @StringRes
    fun getJob(): Int {
        return when (department) {
            DepartmentUiModel.ACTING -> R.string.search_message_actor
            DepartmentUiModel.DIRECTING -> R.string.search_message_director
            DepartmentUiModel.WRITING -> R.string.search_message_writer
            DepartmentUiModel.CREW -> R.string.search_message_crew
        }
    }
}
