package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class ReviewModifyModel(
    @SerializedName("rating")
    val rating: Int,

    @SerializedName("reviewText")
    val reviewText: String?,

    @SerializedName("isAnonymous")
    val isAnonymous: Boolean
)
