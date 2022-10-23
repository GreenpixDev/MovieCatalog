package ru.greenpix.moviecatalog.repository

interface JwtRepository {

    fun getToken(): String?

    fun saveToken(token: String)

    fun deleteToken()

}