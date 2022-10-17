package ru.greenpix.moviecatalog.util

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Double.format(digits: Int) = "%.${digits}f".format(this)