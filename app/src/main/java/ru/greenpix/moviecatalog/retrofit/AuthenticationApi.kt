package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.Body
import retrofit2.http.POST
import ru.greenpix.moviecatalog.dto.JwtTokenDto
import ru.greenpix.moviecatalog.dto.LoginCredentialsDto
import ru.greenpix.moviecatalog.dto.UserRegisterDto

interface AuthenticationApi {

    @POST("/api/account/register")
    suspend fun register(@Body userRegisterModel: UserRegisterDto): JwtTokenDto

    @POST("/api/account/login")
    suspend fun login(@Body loginCredentials: LoginCredentialsDto): JwtTokenDto

    @POST("/api/account/logout")
    suspend fun logout()

}