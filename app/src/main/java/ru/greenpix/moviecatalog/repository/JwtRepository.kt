package ru.greenpix.moviecatalog.repository

/**
 * Репозиторий для взаимодействия с [JWT токенами](https://jwt.io)
 */
interface JwtRepository {

    /**
     * Получить сохраненный [JWT токен](https://jwt.io) в виде строки
     */
    fun getToken(): String?

    /**
     * Сохранить [JWT токен](https://jwt.io) в виде строки
     */
    fun saveToken(token: String)

    /**
     * Удалить сохраненный [JWT токен](https://jwt.io)
     */
    fun deleteToken()

}