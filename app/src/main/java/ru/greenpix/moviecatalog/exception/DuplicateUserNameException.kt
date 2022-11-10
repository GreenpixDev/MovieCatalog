package ru.greenpix.moviecatalog.exception

import retrofit2.HttpException
import retrofit2.Response

/**
 * Исключение, которое выбрасывается во время регистрации, когда на сервере уже есть пользователь
 * с указанным во время регистрации ником.
 */
class DuplicateUserNameException(response: Response<*>) : HttpException(response)