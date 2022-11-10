package ru.greenpix.moviecatalog.repository.util

import java.time.format.DateTimeFormatter

object ServerDateTime {

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

}