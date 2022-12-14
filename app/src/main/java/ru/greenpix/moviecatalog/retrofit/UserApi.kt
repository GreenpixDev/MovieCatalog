package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import ru.greenpix.moviecatalog.dto.ProfileDto
import ru.greenpix.moviecatalog.util.HttpHeader

interface UserApi {

    @GET("/api/account/profile")
    suspend fun getProfile(
        @Header(HttpHeader.AUTHORIZATION) authenticationValue: String
    ): ProfileDto

    @PUT("/api/account/profile")
    suspend fun putProfile(
        @Header(HttpHeader.AUTHORIZATION) authenticationValue: String,
        @Body body: ProfileDto
    )

}