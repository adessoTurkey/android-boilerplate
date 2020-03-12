package com.adesso.movee.internal.util.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    fun either(fnL: (L) -> Any, fnR: () -> Unit): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR()
        }

    fun <T> transform(fnR: (R) -> T): Either<L, T> {
        return when (this) {
            is Left -> this
            is Right -> Right(fnR(b))
        }
    }

    fun ifRight(block: (R) -> Unit) {
        when (this) {
            is Right -> block(b)
            else -> return
        }
    }

    fun ifLeft(block: (L) -> Unit) {
        when (this) {
            is Left -> block(a)
            else -> return
        }
    }
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))
