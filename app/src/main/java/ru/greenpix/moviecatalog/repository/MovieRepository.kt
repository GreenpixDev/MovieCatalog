package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.domain.MovieDetails
import ru.greenpix.moviecatalog.domain.Page

interface MovieRepository {

    suspend fun getPage(pageNumber: Int): Page<Movie>

    suspend fun getDetails(movieId: String): MovieDetails

}