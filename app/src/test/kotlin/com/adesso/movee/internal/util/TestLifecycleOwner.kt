package com.adesso.movee.internal.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestLifecycleOwner : LifecycleOwner, Lifecycle() {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.handleLifecycleEvent(Event.ON_RESUME)
    }

    override val lifecycle: Lifecycle get() = lifecycleRegistry

    override val currentState: State get() = lifecycleRegistry.currentState

    override fun addObserver(observer: LifecycleObserver) {
        lifecycleRegistry.addObserver(observer)
    }

    override fun removeObserver(observer: LifecycleObserver) {
        lifecycleRegistry.removeObserver(observer)
    }
}
