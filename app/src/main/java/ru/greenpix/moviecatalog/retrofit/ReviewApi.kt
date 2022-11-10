package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.*
import ru.greenpix.moviecatalog.dto.ReviewModifyDto
import ru.greenpix.moviecatalog.util.HttpHeader

interface ReviewApi {

    @POST("/api/movie/{movieId}/review/add")
    suspend fun add(
        @Header(HttpHeader.AUTHORIZATION) authenticationValue: String,
        @Path("movieId") movieId: String,
        @Body body: ReviewModifyDto
    )

    @PUT("/api/movie/{movieId}/review/{id}/edit")
    suspend fun edit(
        @Header(HttpHeader.AUTHORIZATION) authenticationValue: String,
        @Path("movieId") movieId: String,
        @Path("id") id: String,
        @Body body: ReviewModifyDto
    )

    @DELETE("/api/movie/{movieId}/review/{id}/delete")
    suspend fun delete(
        @Header(HttpHeader.AUTHORIZATION) authenticationValue: String,
        @Path("movieId") movieId: String,
        @Path("id") id: String
    )

}