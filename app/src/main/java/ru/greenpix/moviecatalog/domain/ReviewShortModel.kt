package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class ReviewShortModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("rating")
    val rating: Int
)