package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("message")
    val message: String,

    @SerializedName("errors")
    val errors: Map<*, *>
)