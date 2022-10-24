package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?
)