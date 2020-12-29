package com.adesso.movee.internal.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 *  Returns formatted string of the given double with given decimal count
 *
 *  @param fractionDigitCount number of decimal point which will be displayed
 *  @return formatted string of given number with the number of fractionDigitCount
 */
fun Double.toDecimalizedString(fractionDigitCount: Int = DEFAULT_FRACTION_DIGIT_COUNT): String {
    val decimalFormat: DecimalFormat =
        NumberFormat.getNumberInstance(Locale.getDefault()) as DecimalFormat
    with(decimalFormat) {
        maximumFractionDigits = fractionDigitCount
        minimumFractionDigits = fractionDigitCount
    }

    return decimalFormat.format(this)
}

private const val DEFAULT_FRACTION_DIGIT_COUNT = 2
