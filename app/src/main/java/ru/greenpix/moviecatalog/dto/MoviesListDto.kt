package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class MoviesListDto(
    @SerializedName("movies")
    val movies: List<MovieElementDto>
)