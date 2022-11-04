package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.MovieDetailsModel
import ru.greenpix.moviecatalog.domain.MoviePagedListModel
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.retrofit.MovieApi

class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getPage(pageNumber: Int): MoviePagedListModel {
        return movieApi.getPage(pageNumber)
    }

    override suspend fun getDetails(movieId: String): MovieDetailsModel {
        return movieApi.getDetails(movieId)
    }
}