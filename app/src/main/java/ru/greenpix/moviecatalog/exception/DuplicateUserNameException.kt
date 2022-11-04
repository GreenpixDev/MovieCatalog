package ru.greenpix.moviecatalog.exception

import retrofit2.HttpException
import retrofit2.Response

class DuplicateUserNameException(response: Response<*>) : HttpException(response)