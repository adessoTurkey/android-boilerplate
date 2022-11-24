package com.adesso.movee.data.remote

import com.adesso.movee.internal.util.Failure
import com.adesso.movee.internal.util.api.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseRemoteDataSource {

    suspend fun <O> invoke(serviceFunction: suspend () -> O): O {
        return try {
            serviceFunction()
        } catch (exception: Exception) {
            throw asFailure(exception)
        }
    }

    suspend fun <O> invokeFlow(serviceFunction: suspend () -> State<O>): Flow<State<O>> = flow {
        emit(serviceFunction())
    }

    private fun asFailure(exception: Exception): Failure {
        return when (exception) {
            is Failure -> exception
            else -> Failure.UnknownError(exception.message ?: "Unknown error")
        }
    }
}
