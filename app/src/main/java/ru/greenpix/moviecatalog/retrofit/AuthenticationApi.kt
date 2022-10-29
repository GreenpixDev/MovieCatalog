package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.Body
import retrofit2.http.POST
import ru.greenpix.moviecatalog.domain.JwtTokenModel
import ru.greenpix.moviecatalog.domain.LoginCredentials
import ru.greenpix.moviecatalog.domain.UserRegisterModel

interface AuthenticationApi {

    @POST("/api/account/register")
    suspend fun register(@Body userRegisterModel: UserRegisterModel): JwtTokenModel

    @POST("/api/account/login")
    suspend fun login(@Body loginCredentials: LoginCredentials): JwtTokenModel

    @POST("/api/account/logout")
    suspend fun logout()

}