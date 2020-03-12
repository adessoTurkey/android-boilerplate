package com.adesso.movee.internal.extension

fun <T> List<T>?.thisOrEmptyList() = this ?: emptyList()
