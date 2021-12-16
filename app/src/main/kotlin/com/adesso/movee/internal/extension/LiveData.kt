package com.adesso.movee.internal.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.internal.util.Event

fun <T> MutableLiveData<T>.notifyDataChange() {
    this.value = this.value
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(
        owner,
        {
            it?.let(observer)
        }
    )
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(
        owner,
        {
            it.getContentIfNotHandled()?.let { t -> observer(t) }
        }
    )
}
