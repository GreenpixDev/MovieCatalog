package ru.greenpix.moviecatalog.usecase

interface AuthorizationUseCase {

    suspend fun <R> withAuthorization(block: suspend (String) -> R): R

}