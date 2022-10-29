package ru.greenpix.moviecatalog.util

fun calcHueByRating(rating: Float): Float {
    return rating / 10f * 120
}