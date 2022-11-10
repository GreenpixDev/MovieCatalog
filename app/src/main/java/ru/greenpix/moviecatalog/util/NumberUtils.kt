package ru.greenpix.moviecatalog.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Функция форматирования [Float] в строку, округляя до [digits] знаков после запятой
 */
fun Float.format(digits: Int) = "%.${digits}f".format(this)

/**
 * Функция форматирования [Double] в строку, округляя до [digits] знаков после запятой
 */
fun Double.format(digits: Int) = "%.${digits}f".format(this)

/**
 * Функция форматирования [Int] в строку, группирируя цифры по 3 штуки, разделяя группы пробелами
 */
fun Int.formatGrouped(): String {
    return NUMBER_FORMAT_GROUPED.format(this)
}

private val NUMBER_FORMAT_GROUPED: NumberFormat by lazy {
    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    val formatSymbols = formatter.decimalFormatSymbols
    formatSymbols.groupingSeparator = ' '
    formatter.decimalFormatSymbols = formatSymbols
    formatter
}