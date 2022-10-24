package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.ReviewModifyModel
import ru.greenpix.moviecatalog.repository.ReviewRepository
import ru.greenpix.moviecatalog.retrofit.ReviewApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class ReviewRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val reviewApi: ReviewApi
) : ReviewRepository {

    override suspend fun addReview(movieId: String, review: ReviewModifyModel) {
        authorizationUseCase.withAuthorization { reviewApi.add(it, movieId, review) }
    }

    override suspend fun updateReview(movieId: String, reviewId: String, review: ReviewModifyModel) {
        authorizationUseCase.withAuthorization { reviewApi.edit(it, movieId, reviewId, review) }
    }

    override suspend fun deleteReview(movieId: String, reviewId: String) {
        authorizationUseCase.withAuthorization { reviewApi.delete(it, movieId, reviewId) }
    }
}