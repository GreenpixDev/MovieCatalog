package ru.greenpix.moviecatalog.domain

import java.time.LocalDate

/**
 * Дата класс, описывающий профиль пользователя в приложении
 */
data class Profile(
    /**
     * Идентификатор пользователя
     */
    val id: String,

    /**
     * Уникальное имя пользователя
     */
    val username: String,

    /**
     * Электронная почта пользователя
     */
    val email: String,

    /**
     * Ссылка на аватарку пользователя
     */
    val avatarUrl: String?,

    /**
     * Имя пользователя
     */
    val name: String,

    /**
     * Дата рождения пользователя
     */
    val birthday: LocalDate,

    /**
     * Пол пользователя.
     *
     * Подробное описание: [Gender]
     */
    val gender: Gender
)
