package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.mapper.MovieMapper
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.retrofit.FavoriteApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class FavoriteRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val favoriteApi: FavoriteApi,
    private val movieMapper: MovieMapper
) : FavoriteRepository {

    override suspend fun getAllFavoriteMovies(): List<Movie> = authorizationUseCase.withAuthorization {
        favoriteApi.getAll(it).movies.map { dto -> movieMapper.map(dto) }
    }

    override suspend fun addFavoriteMovie(movieId: String) = authorizationUseCase.withAuthorization {
        favoriteApi.add(it, movieId)
    }

    override suspend fun deleteFavoriteMovie(movieId: String) = authorizationUseCase.withAuthorization {
        favoriteApi.delete(it, movieId)
    }
}