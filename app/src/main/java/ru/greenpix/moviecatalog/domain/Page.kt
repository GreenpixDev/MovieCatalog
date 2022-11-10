package ru.greenpix.moviecatalog.domain

/**
 * Дата класс, описывающий страницу с контентом типа [T]
 */
data class Page<T>(
    /**
     * Размер страницы (количество элементов в ней)
     */
    val pageSize: Int,

    /**
     * Общее количество страниц
     */
    val totalPageCount: Int,

    /**
     * Порядковый номер страницы
     */
    val pageNumber: Int,

    /**
     * Список с конентом страницы
     */
    val content: List<T>
) {

    /**
     * Свойство, возвращающее true, если страница является первой
     */
    val isFirst: Boolean
        get() = pageNumber == 1

    /**
     * Свойство, возвращающее true, если страница является последней
     */
    val isLast: Boolean
        get() = pageNumber == totalPageCount
}
