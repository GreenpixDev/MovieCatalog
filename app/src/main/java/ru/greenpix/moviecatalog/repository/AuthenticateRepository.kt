package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Gender
import java.time.LocalDate

interface AuthenticateRepository {

    fun isAuthenticated(): Boolean

    suspend fun register(
        login: String,
        email: String,
        name: String,
        password: String,
        birthday: LocalDate,
        gender: Gender
    )

    suspend fun login(
        login: String,
        password: String
    )

    suspend fun logout()

}