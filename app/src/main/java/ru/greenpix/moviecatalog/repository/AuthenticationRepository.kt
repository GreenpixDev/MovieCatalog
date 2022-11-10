package ru.greenpix.moviecatalog.repository

import ru.greenpix.moviecatalog.domain.Gender
import java.time.LocalDate

/**
 * Репозиторий для аутентификации пользователя
 */
interface AuthenticationRepository {

    /**
     * Возвращает true, если пользователь уже аутентифицирован
     */
    fun isAuthenticated(): Boolean

    /**
     * Зарегистрировать пользователя
     *
     * @param login уникальное имя пользователя
     * @param email электронная почта
     * @param name имя пользователя
     * @param password пароль
     * @param birthday дата рождения
     * @param gender пол пользователя
     */
    suspend fun register(
        login: String,
        email: String,
        name: String,
        password: String,
        birthday: LocalDate,
        gender: Gender
    )

    /**
     * Аутентифицировать пользователя
     *
     * @param login уникальное имя пользователя
     * @param password пароль
     */
    suspend fun login(
        login: String,
        password: String
    )

    /**
     * Разлогинить текущего аутентифицированного пользователя
     */
    suspend fun logout()

}