package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Profile

/**
 * Репозиторий для взаимодействия с пользователем
 */
interface UserRepository {

    /**
     * Получить [профиль][Profile] текущего авторизированного пользователя
     */
    suspend fun getProfile(): Profile

    /**
     * Обновить [профиль][Profile] текущего авторизированного пользователя
     */
    suspend fun updateProfile(profile: Profile)

}