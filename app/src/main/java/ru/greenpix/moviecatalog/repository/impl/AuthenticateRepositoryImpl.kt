package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.domain.LoginCredentials
import ru.greenpix.moviecatalog.domain.UserRegisterModel
import ru.greenpix.moviecatalog.exception.AuthenticateException
import ru.greenpix.moviecatalog.repository.AuthenticateRepository
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.retrofit.AuthenticateApi
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

class AuthenticateRepositoryImpl(
    private val jwtRepository: JwtRepository,
    private val authenticateApi: AuthenticateApi
) : AuthenticateRepository {

    private companion object {
        val FORMATTER = DateTimeFormatterBuilder()
            .appendInstant(3)
            .toFormatter()
    }

    override fun isAuthenticated(): Boolean {
        return jwtRepository.getToken() != null
    }

    override suspend fun register(
        login: String,
        email: String,
        name: String,
        password: String,
        birthday: LocalDate,
        gender: Gender
    ) {
        val response = authenticateApi.register(UserRegisterModel(
            username = login,
            email = email,
            name = name,
            password = password,
            birthday = birthday.format(FORMATTER),
            gender = gender
        ))

        if (response.isSuccessful) {
            response.body()?.let { jwtRepository.saveToken(it.token) }
        }
        else {
            throw AuthenticateException()
        }
    }

    override suspend fun login(
        login: String,
        password: String
    ) {
        val response = authenticateApi.login(LoginCredentials(
            username = login,
            password = password
        ))

        if (response.isSuccessful) {
            response.body()?.let { jwtRepository.saveToken(it.token) }
        }
        else {
            throw AuthenticateException()
        }
    }

    override suspend fun logout() {
        jwtRepository.deleteToken()
        authenticateApi.logout()
    }
}