package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import ru.greenpix.moviecatalog.dto.MovieDetailsDto
import ru.greenpix.moviecatalog.dto.MoviesPagedListDto

interface MovieApi {

    @GET("/api/movies/{page}")
    suspend fun getPage(@Path("page") page: Int): MoviesPagedListDto

    @GET("/api/movies/details/{id}")
    suspend fun getDetails(@Path("id") id: String): MovieDetailsDto

}