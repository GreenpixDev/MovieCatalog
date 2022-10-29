package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class JwtTokenModel(
    @SerializedName("token")
    val token: String
)
