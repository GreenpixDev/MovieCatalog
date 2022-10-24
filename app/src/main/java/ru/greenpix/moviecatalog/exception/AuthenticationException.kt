package ru.greenpix.moviecatalog.exception

import retrofit2.HttpException
import retrofit2.Response

class AuthenticationException(response: Response<*>) : HttpException(response)