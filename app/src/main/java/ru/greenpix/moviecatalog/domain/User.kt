package ru.greenpix.moviecatalog.domain

/**
 * Дата класс, описывающий краткую информацию о пользователе
 */
data class User(
    /**
     * Идентификатор пользователя
     */
    val userId: String,

    /**
     * Уникальное имя пользователя
     */
    val username: String?,

    /**
     * Ссылка на аватарку пользователя
     */
    val avatarUrl: String?
)