package ru.greenpix.moviecatalog.util

import com.google.gson.Gson
import retrofit2.Response
import ru.greenpix.moviecatalog.dto.ErrorDto

/**
 * Функция преобразования [ответа от сервера][Response], содердащего ошибку, в [ErrorDto]
 */
fun Gson.fromErrorBody(response: Response<*>?): ErrorDto? {
    return fromJson(response?.errorBody()?.string(), ErrorDto::class.java)
}