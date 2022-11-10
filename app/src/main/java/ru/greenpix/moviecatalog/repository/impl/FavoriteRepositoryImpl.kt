package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.retrofit.FavoriteApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class FavoriteRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val favoriteApi: FavoriteApi
) : FavoriteRepository {

    override suspend fun getAllFavoriteMovies(): List<Movie> = authorizationUseCase.withAuthorization {
        favoriteApi.getAll(it).movies.map { dto ->
            Movie(
                id = dto.id,
                name = dto.name,
                poster = dto.poster,
                year = dto.year,
                country = dto.country,
                genres = dto.genres
                    .mapNotNull { genre -> genre.name },
                rating = dto.reviews
                    .map { review -> review.rating }
                    .average()
            )
        }
    }

    override suspend fun addFavoriteMovie(movieId: String) = authorizationUseCase.withAuthorization {
        favoriteApi.add(it, movieId)
    }

    override suspend fun deleteFavoriteMovie(movieId: String) = authorizationUseCase.withAuthorization {
        favoriteApi.delete(it, movieId)
    }
}