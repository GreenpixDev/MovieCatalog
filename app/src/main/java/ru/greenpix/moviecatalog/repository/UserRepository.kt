package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Profile

interface UserRepository {

    suspend fun getProfile(): Profile

    suspend fun updateProfile(profile: Profile)

}