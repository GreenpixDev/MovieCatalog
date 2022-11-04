package ru.greenpix.moviecatalog.repository.impl

import com.google.gson.Gson
import retrofit2.HttpException
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.domain.LoginCredentials
import ru.greenpix.moviecatalog.domain.UserRegisterModel
import ru.greenpix.moviecatalog.exception.AuthenticationException
import ru.greenpix.moviecatalog.exception.DuplicateUserNameException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.retrofit.AuthenticationApi
import ru.greenpix.moviecatalog.util.HttpCode
import ru.greenpix.moviecatalog.util.fromErrorBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AuthenticationRepositoryImpl(
    private val jwtRepository: JwtRepository,
    private val authenticationApi: AuthenticationApi,
    private val gson: Gson
) : AuthenticationRepository {

    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        const val DUPLICATE_USER_NAME = "DuplicateUserName"
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
        try {
            val jwt = authenticationApi.register(UserRegisterModel(
                username = login,
                email = email,
                name = name,
                password = password,
                birthday = birthday.atStartOfDay().format(FORMATTER),
                gender = gender.ordinal - 1
            ))
            jwtRepository.saveToken(jwt.token)
        }
        catch (e: HttpException) {
            if (e.code() == HttpCode.BAD_REQUEST) {
                val response = e.response()
                val errorBody = gson.fromErrorBody(response)
                if (response != null && errorBody != null) {
                    if (DUPLICATE_USER_NAME in errorBody.errors) {
                        throw DuplicateUserNameException(response)
                    }
                }
            }
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun login(
        login: String,
        password: String
    ) {
        try {
            val jwt = authenticationApi.login(LoginCredentials(
                username = login,
                password = password
            ))
            jwtRepository.saveToken(jwt.token)
        }
        catch (e: HttpException) {
            if (e.code() == HttpCode.BAD_REQUEST) {
                val response = e.response()
                if (response != null) {
                    throw AuthenticationException(response)
                }
            }
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun logout() {
        jwtRepository.deleteToken()
        authenticationApi.logout()
    }
}