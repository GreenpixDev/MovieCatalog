package ru.greenpix.moviecatalog.repository

interface ReviewRepository {

    suspend fun addReview(
        movieId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    )

    suspend fun updateReview(
        movieId: String,
        reviewId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    )

    suspend fun deleteReview(
        movieId: String,
        reviewId: String
    )

}