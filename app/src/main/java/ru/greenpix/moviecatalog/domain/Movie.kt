package ru.greenpix.moviecatalog.domain

data class Movie(
    val id: String,
    val name: String?,
    val poster: String?,
    val year: Int,
    val country: String?,
    val genres: List<String>,
    val rating: Double
)