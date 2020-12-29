package com.adesso.movee.internal.util

import java.io.IOException

sealed class Failure : IOException() {
    class ApiError(var code: Int, override var message: String) : Failure()
    class UnknownError(val exception: Exception) : Failure()
    class TimeOutError(override var message: String?) : Failure()
    object NoConnectivityError : Failure()
    object EmptyResponse : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
