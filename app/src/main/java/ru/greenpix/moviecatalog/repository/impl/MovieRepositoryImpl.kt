package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.domain.MovieDetails
import ru.greenpix.moviecatalog.domain.Page
import ru.greenpix.moviecatalog.mapper.MovieDetailsMapper
import ru.greenpix.moviecatalog.mapper.MoviesPageMapper
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.retrofit.MovieApi

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val moviesPageMapper: MoviesPageMapper,
    private val movieDetailsMapper: MovieDetailsMapper
) : MovieRepository {

    override suspend fun getPage(pageNumber: Int): Page<Movie> {
        return movieApi.getPage(pageNumber).let { moviesPageMapper.map(it) }
    }

    override suspend fun getDetails(movieId: String): MovieDetails {
        return movieApi.getDetails(movieId).let { movieDetailsMapper.map(it) }
    }
}