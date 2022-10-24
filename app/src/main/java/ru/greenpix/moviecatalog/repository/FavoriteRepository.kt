package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.MovieElementModel

interface FavoriteRepository {

    suspend fun getAllFavoriteMovies(): List<MovieElementModel>

    suspend fun addFavoriteMovie(movieId: String)

    suspend fun deleteFavoriteMovie(movieId: String)

}