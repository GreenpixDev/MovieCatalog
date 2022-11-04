package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.ProfileModel
import ru.greenpix.moviecatalog.repository.UserRepository
import ru.greenpix.moviecatalog.retrofit.UserApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase

class UserRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getProfile(): ProfileModel {
        return authorizationUseCase.withAuthorization { userApi.getProfile(it) }
    }

    override suspend fun updateProfile(profile: ProfileModel) {
        authorizationUseCase.withAuthorization { userApi.putProfile(it, profile) }
    }
}