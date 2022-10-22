package ru.greenpix.moviecatalog.util

fun calcHueByRating(rating: Float): Float {
    return (rating - 1) / 9f * 120
}