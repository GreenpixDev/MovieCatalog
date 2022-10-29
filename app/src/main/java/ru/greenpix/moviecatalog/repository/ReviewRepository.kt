package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.ReviewModifyModel

interface ReviewRepository {

    suspend fun addReview(movieId: String, review: ReviewModifyModel)

    suspend fun updateReview(movieId: String, reviewId: String, review: ReviewModifyModel)

    suspend fun deleteReview(movieId: String, reviewId: String)

}