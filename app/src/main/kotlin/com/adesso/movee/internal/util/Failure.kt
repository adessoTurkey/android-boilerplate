package com.adesso.movee.internal.util

import java.io.IOException
import java.lang.Exception

sealed class Failure : IOException() {
    class ServerError(var code: Int = 0, override var message: String) : Failure()
    class UnAuthError(override var message: String) : Failure()
    class UnknownHostError : Failure()
    class JsonError : Failure()
    class ApiError(var code: Int, override var message: String) : Failure()
    class UnknownError(val exception: Exception) : Failure()
    class FormValidationError(override var message: String? = null) : Failure()
    class HttpError(var code: Int, override var message: String) : Failure()
    class TimeOutError(override var message: String?) : Failure()
    object IoError : Failure()
    object NoConnectivityError : Failure()
    object EmptyResponse : Failure()
    object IgnorableError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
