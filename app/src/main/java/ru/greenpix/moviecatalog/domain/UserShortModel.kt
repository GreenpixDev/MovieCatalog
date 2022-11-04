package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class UserShortModel(
    @SerializedName("userId")
    val userId: String,

    @SerializedName("nickName")
    val nickName: String?,

    @SerializedName("avatar")
    val avatar: String?
)