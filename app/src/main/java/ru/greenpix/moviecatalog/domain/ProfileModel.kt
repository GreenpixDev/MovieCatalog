package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("nickName")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatarLink")
    val avatarLink: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("birthDate")
    val birthday: String,

    @SerializedName("gender")
    val gender: Int
)
