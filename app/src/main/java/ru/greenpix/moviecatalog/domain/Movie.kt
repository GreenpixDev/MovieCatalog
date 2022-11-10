package ru.greenpix.moviecatalog.domain

/**
 * Дата класс, описывающий основную информацию о фильме
 */
data class Movie(
    /**
     * Идентификатор фильма
     */
    val id: String,

    /**
     * Название фильма
     */
    val name: String?,

    /**
     * Ссылка на постер (картинку) фильма
     */
    val posterUrl: String?,

    /**
     * Год выпуска фильма
     */
    val year: Int,

    /**
     * В какой стране был выпущен фильм
     */
    val country: String?,

    /**
     * Список жанров фильмов
     */
    val genres: List<String>,

    /**
     * Общий рейтинг фильма
     */
    val rating: Double
)