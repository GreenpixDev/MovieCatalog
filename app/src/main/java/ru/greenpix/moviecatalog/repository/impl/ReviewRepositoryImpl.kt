package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.dto.ReviewModifyDto
import ru.greenpix.moviecatalog.repository.ReviewRepository
import ru.greenpix.moviecatalog.retrofit.ReviewApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class ReviewRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val reviewApi: ReviewApi
) : ReviewRepository {

    override suspend fun addReview(
        movieId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean,
    ) = authorizationUseCase.withAuthorization {
        reviewApi.add(it, movieId, ReviewModifyDto(
            rating = rating,
            reviewText = reviewText,
            isAnonymous = isAnonymous
        ))
    }

    override suspend fun updateReview(
        movieId: String,
        reviewId: String,
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    ) = authorizationUseCase.withAuthorization {
        reviewApi.edit(it, movieId, reviewId, ReviewModifyDto(
            rating = rating,
            reviewText = reviewText,
            isAnonymous = isAnonymous
        ))
    }

    override suspend fun deleteReview(
        movieId: String,
        reviewId: String
    ) = authorizationUseCase.withAuthorization {
        reviewApi.delete(it, movieId, reviewId)
    }
}