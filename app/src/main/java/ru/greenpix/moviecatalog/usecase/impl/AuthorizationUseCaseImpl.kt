package ru.greenpix.moviecatalog.usecase.impl

import retrofit2.HttpException
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase
import ru.greenpix.moviecatalog.util.HttpCode

class AuthorizationUseCaseImpl(
    private val jwtRepository: JwtRepository,
    private val authenticationRepository: AuthenticationRepository
) : AuthorizationUseCase {

    private companion object {
        const val AUTHORIZATION_METHOD = "Bearer"
    }

    override suspend fun <R> withAuthorization(block: suspend (String) -> R): R {
        try {
            return jwtRepository.getToken()?.let {
                block.invoke("$AUTHORIZATION_METHOD $it")
            } ?: run {
                throw AuthorizationException()
            }
        }
        catch (e: HttpException) {
            if (e.code() == HttpCode.UNAUTHORIZED) {
                authenticationRepository.logout()
                throw AuthorizationException()
            }
            e.printStackTrace()
            throw e
        }
    }
}