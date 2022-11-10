package ru.greenpix.moviecatalog.repository.util

import java.time.format.DateTimeFormatter

object ServerDateTime {

    /**
     * Объект, преобразующий дату и время в формат, который сервер в состоянии интерпретировать
     */
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

}