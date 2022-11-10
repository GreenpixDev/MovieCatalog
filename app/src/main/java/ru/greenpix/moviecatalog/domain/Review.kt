package ru.greenpix.moviecatalog.domain

import java.time.LocalDateTime

data class Review(
    val id: String,
    val rating: Int,
    val reviewText: String?,
    val isAnonymous: Boolean,
    val createDateTime: LocalDateTime,
    val author: User?
)