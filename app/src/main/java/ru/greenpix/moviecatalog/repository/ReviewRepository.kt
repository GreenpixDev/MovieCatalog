package ru.greenpix.moviecatalog.repository

/**
 * Репозиторий для взаимодействия с рецензиями
 */
interface ReviewRepository {

    /**
     * Добавить рецензию к фильму с идентификатором [movieId].
     *
     * @param rating рейтинг рецензии (число от 0 до 10 включительно)
     * @param reviewText текст рецензии
     * @param isAnonymous true, если рецензия анонимная
     */
    suspend fun addReview(
        movieId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    )

    /**
     * Обновить рецензию с идентификатором [reviewId] у фильма с идентификатором [movieId].
     *
     * @param rating рейтинг рецензии (число от 0 до 10 включительно)
     * @param reviewText текст рецензии
     * @param isAnonymous true, если рецензия анонимная
     */
    suspend fun updateReview(
        movieId: String,
        reviewId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    )

    /**
     * Удалить рецензию с идентификатором [reviewId] у фильма с идентификатором [movieId].
     */
    suspend fun deleteReview(
        movieId: String,
        reviewId: String
    )

}