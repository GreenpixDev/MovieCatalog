package ru.greenpix.moviecatalog.domain

import java.time.LocalDateTime

/**
 * Дата класс, описывающий рецензии (комментарии) к фильму
 */
data class Review(
    /**
     * Идентификатор рецензии
     */
    val id: String,

    /**
     * Рейтинг рецензии (число от 0 до 10 включительно)
     */
    val rating: Int,

    /**
     * Текст рецензии
     */
    val reviewText: String?,

    /**
     * Анонимная ли рецензия?
     */
    val isAnonymous: Boolean,

    /**
     * Дата создания рецензии
     */
    val createDateTime: LocalDateTime,

    /**
     * Автор рецензии.
     *
     * Подробное описание: [User]
     */
    val author: User?
)