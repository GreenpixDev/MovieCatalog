package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class JwtTokenDto(
    @SerializedName("token")
    val token: String
)
