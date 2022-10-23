package ru.greenpix.moviecatalog.ui.view.screen.home.main.model

import ru.greenpix.moviecatalog.util.calcHueByRating

data class MainMovie(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val year: Int,
    val country: String,
    val genres: String,
    val rating: Float,
    val hue: Float = calcHueByRating(rating),
)