package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Movie

/**
 * Репозиторий для взаимодействия с любимыми фильмами
 */
interface FavoriteRepository {

    /**
     * Получить список всех любимых [фильмов][Movie] у текущего авторизированного пользователя
     */
    suspend fun getAllFavoriteMovies(): List<Movie>

    /**
     * Добавить фильм с идентификатором [movieId] в любимые у текущего авторизированного пользователя
     */
    suspend fun addFavoriteMovie(movieId: String)

    /**
     * Удалить фильм с идентификатором [movieId] из любимых у текущего авторизированного пользователя
     */
    suspend fun deleteFavoriteMovie(movieId: String)

}