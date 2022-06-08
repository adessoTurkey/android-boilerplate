package com.adesso.movee.internal.extension

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.api.State

fun <T1, T2, R> State<T1>.combineSuccess(
    child: T2,
    call: (T1, T2) -> R
): State<R> {
    return when (this) {
        is State.Success -> State.Success(call.invoke(this.data, child))
        is State.Fail -> State.Fail(this.failure)
    }
}

/**
 * Transforms [State] object type. It is generally used to change [State.Success.data].
 * @param call higher order function is for getting new object.
 * @return [State.Success.data] with new new type of object.
 */
fun <In, Out> State<In>.transformSuccess(call: (In) -> Out): State<Out> = when (this) {
    is State.Success -> State.Success(call.invoke(this.data))
    is State.Fail -> State.Fail(this.failure)
}

/**
 * Main purpose is to work with [State.Success.data] and pass other state to upper functions.
 *
 * @param call higher order function is for giving the object.
 * @return self with same type of [State.Success.data] object.
 */
fun <In> State<In>.onSuccess(call: (In) -> Unit): State<In> {
    if (this is State.Success) {
        call.invoke(this.data)
    }
    return this
}

fun <In> State<In>.doOnFailed(call: (Failure) -> Unit): State<In> {
    if (this is State.Fail) {
        call.invoke(this.failure)
    }
    return this
}
