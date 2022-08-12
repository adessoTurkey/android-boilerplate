package com.papara.merchant.internal.util.flow

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Params, out T : Any> {
    abstract suspend fun execute(params: Params): Flow<T>
}
