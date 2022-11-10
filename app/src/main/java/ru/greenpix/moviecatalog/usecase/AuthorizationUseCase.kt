package ru.greenpix.moviecatalog.usecase

/**
 * Use case для авторизации перед взаимодействием с сервером
 */
interface AuthorizationUseCase {

    /**
     * Метод, который выполняет [block] кода в контексте авторизации.
     *
     * В лямбда функцию [block] передается параметр строки с токеном авторизации.
     *
     * Если авторизоваться не получилось, метод выбросит исключение [ru.greenpix.moviecatalog.exception.AuthorizationException]
     */
    suspend fun <R> withAuthorization(block: suspend (String) -> R): R

}