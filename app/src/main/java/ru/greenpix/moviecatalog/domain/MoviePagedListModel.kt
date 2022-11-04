package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class MoviePagedListModel(
    @SerializedName("movies")
    val movies: List<MovieElementModel>,

    @SerializedName("pageInfo")
    val pageInfo: PageInfoModel
)