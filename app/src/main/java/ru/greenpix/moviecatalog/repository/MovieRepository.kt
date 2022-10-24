package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.MovieDetailsModel
import ru.greenpix.moviecatalog.domain.MoviePagedListModel

interface MovieRepository {

    suspend fun getPage(pageNumber: Int): MoviePagedListModel

    suspend fun getDetails(movieId: String): MovieDetailsModel

}