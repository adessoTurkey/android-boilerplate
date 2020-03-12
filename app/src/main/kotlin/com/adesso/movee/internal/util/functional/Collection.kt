package com.adesso.movee.internal.util.functional

fun <T> zip(vararg lists: List<T>): List<List<T>> {
    return zip(*lists, transform = { it })
}

private inline fun <T, V> zip(vararg lists: List<T>, transform: (List<T>) -> V): List<V> {
    val minSize = lists.map(List<T>::size).min() ?: return emptyList()
    val list = ArrayList<V>(minSize)

    val iterators = lists.map { it.iterator() }

    (0 until minSize).forEach { _ ->
        list.add(transform(iterators.map { it.next() }))
    }

    return list
}
