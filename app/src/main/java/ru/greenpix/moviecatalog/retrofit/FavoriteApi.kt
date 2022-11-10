package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.*
import ru.greenpix.moviecatalog.dto.MoviesListDto
import ru.greenpix.moviecatalog.util.HttpHeader

interface FavoriteApi {

    @GET("/api/favorites")
    suspend fun getAll(
        @Header(HttpHeader.AUTHORIZATION) authorizationValue: String
    ): MoviesListDto

    @POST("/api/favorites/{id}/add")
    suspend fun add(
        @Header(HttpHeader.AUTHORIZATION) authorizationValue: String,
        @Path("id") id: String
    )

    @DELETE("/api/favorites/{id}/delete")
    suspend fun delete(
        @Header(HttpHeader.AUTHORIZATION) authorizationValue: String,
        @Path("id") id: String
    )

}