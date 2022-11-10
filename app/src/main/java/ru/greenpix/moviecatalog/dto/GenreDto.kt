package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?
)