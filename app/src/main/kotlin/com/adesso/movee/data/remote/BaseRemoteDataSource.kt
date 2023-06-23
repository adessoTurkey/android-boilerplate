package com.adesso.movee.data.remote

import com.adesso.movee.internal.util.Failure
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result

open class BaseRemoteDataSource {

    suspend fun <O> invoke(serviceFunction: suspend () -> Result<O, Failure>): Result<O, Failure> {
        return try {
            serviceFunction()
        } catch (exception: Exception) {
            Err(asFailure(exception))
        }
    }

    private fun asFailure(exception: Exception): Failure {
        return exception as? Failure ?: Failure.UnknownError(exception.message ?: "Unknown error")
    }
}
