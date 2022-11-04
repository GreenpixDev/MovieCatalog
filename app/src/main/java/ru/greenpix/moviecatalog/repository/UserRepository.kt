package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.ProfileModel

interface UserRepository {

    suspend fun getProfile(): ProfileModel

    suspend fun updateProfile(profile: ProfileModel)

}