package ru.greenpix.moviecatalog.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Double.format(digits: Int) = "%.${digits}f".format(this)

private val NUMBER_FORMAT_GROUPED: NumberFormat by lazy {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    val formatSymbols = formatter.decimalFormatSymbols
    formatSymbols.groupingSeparator = ' '
    formatter.decimalFormatSymbols = formatSymbols
    formatter
}

fun Int.formatGrouped(): String {
    return NUMBER_FORMAT_GROUPED.format(this)
}