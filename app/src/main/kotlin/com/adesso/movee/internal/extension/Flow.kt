package com.adesso.movee.internal.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.api.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

fun <In, Out> Flow<State<In>>.mapSuccess(call: (In) -> Out): Flow<State<Out>> =
    this.map {
        it.transformSuccess(call)
    }

fun <In> Flow<State<In>>.doOnFailed(onFailed: (Failure) -> Unit): Flow<In> =
    this.mapNotNull {
        when (it) {
            is State.Fail -> {
                onFailed.invoke(it.failure)
                null
            }

            is State.Success -> it.data
        }
    }

fun <T> Flow<T>.observeIn(lifecycleOwner: LifecycleOwner): Job {
    return flowWithLifecycle(lifecycleOwner.lifecycle)
        .launchIn(lifecycleOwner.lifecycle.coroutineScope)
}

fun <T> Flow<T>.observeIn(fragment: Fragment): Job {
    return observeIn(fragment.viewLifecycleOwner)
}
