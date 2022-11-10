package ru.greenpix.moviecatalog.exception

import retrofit2.HttpException
import retrofit2.Response

/**
 * Исключение, которое выбрасывается, когда не удалось провести аутентификацию пользователя на сервере
 */
class AuthenticationException(response: Response<*>) : HttpException(response)