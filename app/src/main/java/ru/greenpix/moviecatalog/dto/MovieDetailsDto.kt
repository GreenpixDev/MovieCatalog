package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
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
    val genres: List<GenreDto>,

    @SerializedName("reviews")
    val reviews: List<ReviewDto>,

    @SerializedName("time")
    val time: Int,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("director")
    val director: String?,

    @SerializedName("budget")
    val budget: Int?,

    @SerializedName("fees")
    val fees: Int?,

    @SerializedName("ageLimit")
    val ageLimit: Int
)