package com.adesso.movee.internal.util

import java.io.IOException

sealed class Failure : IOException() {
    class NetworkError(override val message: String) : Failure()
    class UnknownError(override val message: String) : Failure()
    object TimeOutError : Failure()
    object NoConnectivityError : Failure() {
        const val errorCode: Int = 400
        const val errorMessage = "No Internet Connection"
    }

    object EmptyResponse : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
