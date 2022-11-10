package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class LoginCredentialsDto(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)