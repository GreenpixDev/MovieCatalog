package ru.greenpix.moviecatalog.domain

/**
 * Дата класс, описывающий детальную информацию о фильме
 */
data class MovieDetails(
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
     * Список всех рецензий (комментариев) на фильм
     */
    val reviews: List<Review>,

    /**
     * Длительность фильма
     */
    val time: Int,

    /**
     * Слоган фильма
     */
    val tagline: String?,

    /**
     * Описание фильма
     */
    val description: String?,

    /**
     * Режиссёр фильма
     */
    val director: String?,

    /**
     * Бюджет фильма
     */
    val budget: Int?,

    /**
     * Сборы в мире
     */
    val fees: Int?,

    /**
     * Возрастное ограничение
     */
    val ageLimit: Int
)