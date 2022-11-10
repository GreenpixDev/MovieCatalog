package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.domain.MovieDetails
import ru.greenpix.moviecatalog.domain.Page

/**
 * Репозиторий для взаимодействия с фильмами
 */
interface MovieRepository {

    /**
     * Получить [страницу][Page] [фильмов][Movie] номер [pageNumber]
     */
    suspend fun getPage(pageNumber: Int): Page<Movie>

    /**
     * Получить [детальную информацию о фильме][MovieDetails] с идентификатором [movieId]
     */
    suspend fun getDetails(movieId: String): MovieDetails

}