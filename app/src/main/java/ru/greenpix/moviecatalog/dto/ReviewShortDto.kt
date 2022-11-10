package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class ReviewShortDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("rating")
    val rating: Int
)