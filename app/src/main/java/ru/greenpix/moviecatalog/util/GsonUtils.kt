package ru.greenpix.moviecatalog.util

import com.google.gson.Gson
import retrofit2.Response
import ru.greenpix.moviecatalog.domain.ErrorModel

fun Gson.fromErrorBody(response: Response<*>?): ErrorModel? {
    return fromJson(response?.errorBody()?.string(), ErrorModel::class.java)
}