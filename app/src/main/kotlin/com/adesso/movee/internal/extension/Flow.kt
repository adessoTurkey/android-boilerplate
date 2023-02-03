package com.adesso.movee.internal.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

fun <OkTypeIn, ErrType, OkTypeOut> Flow<Result<OkTypeIn, ErrType>>.mapSuccess(
    call: (OkTypeIn) -> OkTypeOut
): Flow<Result<OkTypeOut, ErrType>> =
    this.map {
        when (it) {
            is Ok -> Ok(call.invoke(it.value))
            is Err -> it
        }
    }

fun <OkType, ErrType> Flow<Result<OkType, ErrType>>.doOnFailed(
    onFailed: (ErrType) -> Unit
): Flow<Result<OkType, ErrType>> =
    this.mapNotNull {
        when (it) {
            is Ok -> it
            is Err -> {
                onFailed.invoke(it.error)
                null
            }
        }
    }

fun <T> Flow<T>.observeIn(lifecycleOwner: LifecycleOwner): Job {
    return flowWithLifecycle(lifecycleOwner.lifecycle)
        .launchIn(lifecycleOwner.lifecycle.coroutineScope)
}

fun <T> Flow<T>.observeIn(fragment: Fragment): Job {
    return observeIn(fragment.viewLifecycleOwner)
}
