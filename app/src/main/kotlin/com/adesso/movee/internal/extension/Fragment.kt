package com.adesso.movee.internal.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun Fragment.addOnBackPressedDispatcher(onBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    )
}

inline fun <T> Fragment.collectFlow(
    flow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleScope.launch {
        flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, minActiveState).collect { action.invoke(it) }
    }
}
