package ru.greenpix.moviecatalog.dto

import com.google.gson.annotations.SerializedName

data class PageInfoDto(
    @SerializedName("pageSize")
    val pageSize: Int,

    @SerializedName("pageCount")
    val pageCount: Int,

    @SerializedName("currentPage")
    val currentPage: Int
)
