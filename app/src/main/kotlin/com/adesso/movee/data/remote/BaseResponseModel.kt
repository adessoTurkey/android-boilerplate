package com.adesso.movee.data.remote

import android.os.Parcelable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseResponseModel : Parcelable {

    fun toJson(moshi: Moshi): String {
        val jsonAdapter: JsonAdapter<BaseResponseModel> = moshi.adapter(this.javaClass)

        return jsonAdapter.toJson(this)
    }
}
