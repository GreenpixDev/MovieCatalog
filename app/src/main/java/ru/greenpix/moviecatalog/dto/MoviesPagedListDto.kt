package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class MoviesPagedListDto(
    @SerializedName("movies")
    val movies: List<MovieElementDto>,

    @SerializedName("pageInfo")
    val pageInfo: PageInfoDto
)