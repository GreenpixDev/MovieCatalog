package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class UserShortDto(
    @SerializedName("userId")
    val userId: String,

    @SerializedName("nickName")
    val nickName: String?,

    @SerializedName("avatar")
    val avatar: String?
)