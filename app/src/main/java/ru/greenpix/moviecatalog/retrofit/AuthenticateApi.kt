package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.Body
import retrofit2.http.POST
import ru.greenpix.moviecatalog.domain.JwtToken
import ru.greenpix.moviecatalog.domain.LoginCredentials
import ru.greenpix.moviecatalog.domain.UserRegisterModel

interface AuthenticateApi {

    @POST("/api/account/register")
    suspend fun register(@Body userRegisterModel: UserRegisterModel): JwtToken

    @POST("/api/account/login")
    suspend fun login(@Body loginCredentials: LoginCredentials): JwtToken

    @POST("/api/account/logout")
    suspend fun logout()

}