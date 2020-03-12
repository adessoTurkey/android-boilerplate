package com.adesso.movee.data.local.delegate

import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import com.adesso.movee.data.local.BaseLocalModel
import com.adesso.movee.internal.extension.put
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ListPreference<T : BaseLocalModel>(
    private val preferences: SharedPreferences,
    private val preferenceName: String,
    private val jsonAdapter: JsonAdapter<List<T>>
) : ReadWriteProperty<Any, List<T>?> {

    @WorkerThread
    override fun getValue(thisRef: Any, property: KProperty<*>): List<T>? {
        return preferences.getString(preferenceName, null)?.let {
            jsonAdapter.fromJson(it)
        }
    }

    @WorkerThread
    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>?) {
        preferences.put {
            putString(preferenceName, jsonAdapter.toJson(value))
        }
    }
}

inline fun <reified T : BaseLocalModel> listPreference(
    moshi: Moshi,
    preferences: SharedPreferences,
    preferenceName: String
): ListPreference<T> {
    val types = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(types)
    return ListPreference(
        preferences = preferences,
        preferenceName = preferenceName,
        jsonAdapter = adapter
    )
}
