package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class JwtToken(
    @SerializedName("token")
    val token: String
)
