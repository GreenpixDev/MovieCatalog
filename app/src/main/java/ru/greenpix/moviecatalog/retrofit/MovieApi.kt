package ru.greenpix.moviecatalog.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import ru.greenpix.moviecatalog.domain.MovieDetailsModel
import ru.greenpix.moviecatalog.domain.MoviePagedListModel

interface MovieApi {

    @GET("/api/movies/{page}")
    suspend fun getPage(@Path("page") page: Int): MoviePagedListModel

    @GET("/api/movies/details/{id}")
    suspend fun getDetails(@Path("id") id: String): MovieDetailsModel

}