package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class ReviewModel(
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
    val author: UserShortModel?
)