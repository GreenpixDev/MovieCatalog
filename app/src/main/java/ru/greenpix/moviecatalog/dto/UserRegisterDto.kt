package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class UserRegisterDto(
    @SerializedName("userName")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("birthDate")
    val birthday: String,

    @SerializedName("gender")
    val gender: Int
)
