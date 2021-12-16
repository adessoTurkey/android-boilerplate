package com.adesso.movee.internal.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.adesso.movee.internal.util.Event

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(
        this,
        {
            it?.let { t -> observer(t) }
        }
    )
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<Event<T>>, observer: (T) -> Unit) {
    liveData.observe(
        this,
        {
            it.getContentIfNotHandled()?.let { t -> observer(t) }
        }
    )
}
