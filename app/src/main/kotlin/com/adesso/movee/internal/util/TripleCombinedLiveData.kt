package com.adesso.movee.internal.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class TripleCombinedLiveData<M, N, O, S>(
    source1: LiveData<M>,
    source2: LiveData<N>,
    source3: LiveData<O>,
    private val combine: (M?, N?, O?) -> S
) : MediatorLiveData<S>() {

    private var data1: M? = null
    private var data2: N? = null
    private var data3: O? = null

    init {
        super.addSource(source1) {
            data1 = it
            value = combine(data1, data2, data3)
        }

        super.addSource(source2) {
            data2 = it
            value = combine(data1, data2, data3)
        }

        super.addSource(source3) {
            data3 = it
            value = combine(data1, data2, data3)
        }
    }

    override fun <T : Any?> addSource(source: LiveData<T>, onChanged: Observer<in T>) {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> removeSource(toRemote: LiveData<T>) {
        throw UnsupportedOperationException()
    }
}
