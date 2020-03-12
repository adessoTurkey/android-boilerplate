package com.adesso.movee.internal.util

import com.adesso.movee.internal.util.functional.Either

abstract class UseCase<out Type, in Params> where Type : Any {

    protected abstract suspend fun buildUseCase(params: Params): Type

    suspend fun run(params: Params): Either<Failure, Type> {
        return try {
            Either.Right(buildUseCase(params))
        } catch (failure: Failure) {
            Either.Left(failure)
        }
    }

    object None {
        override fun toString() = "UseCase.None"
    }
}
