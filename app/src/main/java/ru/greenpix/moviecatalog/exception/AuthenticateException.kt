package ru.greenpix.moviecatalog.exception

import retrofit2.HttpException
import retrofit2.Response

class AuthenticateException(response: Response<*>) : HttpException(response)