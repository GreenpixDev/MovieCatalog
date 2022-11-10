package ru.greenpix.moviecatalog.domain

import java.time.LocalDate

data class Profile(
    val id: String,
    val username: String,
    val email: String,
    val avatarLink: String?,
    val name: String,
    val birthday: LocalDate,
    val gender: Gender
)
