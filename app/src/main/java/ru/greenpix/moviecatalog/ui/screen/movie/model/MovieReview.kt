package ru.greenpix.moviecatalog.ui.screen.movie.model

import ru.greenpix.moviecatalog.util.calcHueByRating

data class MovieReview(
    val id: String,
    val author: String,
    val avatarUrl: String,
    val comment: String,
    val anonymous: Boolean,
    val date: String,
    val rating: Int,
    val hue: Float = calcHueByRating(rating.toFloat())
)
