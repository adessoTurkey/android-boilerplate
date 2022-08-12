package com.papara.merchant.internal.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestLifecycleOwner : LifecycleOwner, Lifecycle() {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.handleLifecycleEvent(Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun getCurrentState(): State = lifecycleRegistry.currentState

    override fun addObserver(observer: LifecycleObserver) {
        lifecycleRegistry.addObserver(observer)
    }

    override fun removeObserver(observer: LifecycleObserver) {
        lifecycleRegistry.removeObserver(observer)
    }
}
