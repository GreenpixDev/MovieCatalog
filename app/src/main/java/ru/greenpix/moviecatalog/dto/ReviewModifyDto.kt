package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class ReviewModifyDto(
    @SerializedName("rating")
    val rating: Int,

    @SerializedName("reviewText")
    val reviewText: String?,

    @SerializedName("isAnonymous")
    val isAnonymous: Boolean
)
