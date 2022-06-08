package com.adesso.movee.internal.extension

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.api.State
import kotlinx.coroutines.flow.Flow
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
