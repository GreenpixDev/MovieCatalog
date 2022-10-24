package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class MovieElementModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("poster")
    val poster: String?,

    @SerializedName("year")
    val year: Int,

    @SerializedName("country")
    val country: String?,

    @SerializedName("genres")
    val genres: List<GenreModel>,

    @SerializedName("reviews")
    val reviews: List<ReviewShortModel>
)
