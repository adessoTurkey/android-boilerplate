package com.adesso.movee.internal.util

sealed class Failure : Exception() {
    class NetworkError(override val message: String) : Failure()
    class UnknownError(override val message: String) : Failure()
    object TimeOutError : Failure()
    object NoConnectivityError : Failure()
    object EmptyResponse : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
