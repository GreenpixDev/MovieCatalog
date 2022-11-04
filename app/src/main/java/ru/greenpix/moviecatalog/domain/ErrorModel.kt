package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("message")
    val message: String,

    @SerializedName("errors")
    val errors: Map<*, *>
)