package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("rating")
    val rating: Int,

    @SerializedName("reviewText")
    val reviewText: String?,

    @SerializedName("isAnonymous")
    val isAnonymous: Boolean,

    @SerializedName("createDateTime")
    val createDateTime: String,

    @SerializedName("author")
    val author: UserShortDto?
)