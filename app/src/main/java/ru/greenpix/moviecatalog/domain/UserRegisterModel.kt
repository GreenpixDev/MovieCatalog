package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class UserRegisterModel(
    @SerializedName("userName")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("gender")
    val gender: Int
)
