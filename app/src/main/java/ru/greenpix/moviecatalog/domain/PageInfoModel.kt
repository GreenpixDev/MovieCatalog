package ru.greenpix.moviecatalog.domain

import com.google.gson.annotations.SerializedName

data class PageInfoModel(
    @SerializedName("pageSize")
    val pageSize: Int,

    @SerializedName("pageCount")
    val pageCount: Int,

    @SerializedName("currentPage")
    val currentPage: Int
)
