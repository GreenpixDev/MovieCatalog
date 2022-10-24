package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class MoviesListModel(
    @SerializedName("movies")
    val movies: List<MovieElementModel>
)