package ru.greenpix.moviecatalog.domain

data class Page<T>(
    val pageSize: Int,
    val totalPageCount: Int,
    val pageNumber: Int,
    val content: List<T>
)
