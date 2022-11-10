package ru.greenpix.moviecatalog.util

import com.google.gson.Gson
import retrofit2.Response
import ru.greenpix.moviecatalog.dto.ErrorDto

fun Gson.fromErrorBody(response: Response<*>?): ErrorDto? {
    return fromJson(response?.errorBody()?.string(), ErrorDto::class.java)
}