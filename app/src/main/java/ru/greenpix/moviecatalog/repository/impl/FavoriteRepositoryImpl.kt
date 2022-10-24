package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.MovieElementModel
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.retrofit.FavoriteApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class FavoriteRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val favoriteApi: FavoriteApi
) : FavoriteRepository {

    override suspend fun getAllFavoriteMovies(): List<MovieElementModel> {
        return authorizationUseCase.withAuthorization { favoriteApi.getAll(it) }.movies
    }

    override suspend fun addFavoriteMovie(movieId: String) {
        authorizationUseCase.withAuthorization { favoriteApi.add(it, movieId) }
    }

    override suspend fun deleteFavoriteMovie(movieId: String) {
        authorizationUseCase.withAuthorization { favoriteApi.delete(it, movieId) }
    }
}