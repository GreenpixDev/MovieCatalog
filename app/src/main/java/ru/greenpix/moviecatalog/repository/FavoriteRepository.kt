package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Movie

interface FavoriteRepository {

    suspend fun getAllFavoriteMovies(): List<Movie>

    suspend fun addFavoriteMovie(movieId: String)

    suspend fun deleteFavoriteMovie(movieId: String)

}