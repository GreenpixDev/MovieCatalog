package ru.greenpix.moviecatalog.ui.view.screen.main.model

import ru.greenpix.moviecatalog.util.calcHueByRating

data class MainMovie(
    val id: String,
    val name: String,
    val imageUrl: String,
    val year: Int,
    val country: String,
    val genres: String,
    val rating: Float,
    val hue: Float = calcHueByRating(rating),
)