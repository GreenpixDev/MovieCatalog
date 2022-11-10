package ru.greenpix.moviecatalog.usecase

import ru.greenpix.moviecatalog.usecase.model.ValidationResult
import java.time.LocalDate

/**
 * Use case для валидации данных
 */
interface ValidationUseCase {

    /**
     * Метод валидации уникального имени пользователя.
     * Возвращает [ValidationResult]
     */
    fun validateLogin(login: String): ValidationResult

    /**
     * Метод валидации пароля.
     * Возвращает [ValidationResult]
     */
    fun validatePassword(password: String): ValidationResult

    /**
     * Метод валидации электронной почты.
     * Возвращает [ValidationResult]
     */
    fun validateEmail(email: String): ValidationResult

    /**
     * Метод валидации ссылки.
     * Возвращает [ValidationResult]
     */
    fun validateUrl(url: String): ValidationResult

    /**
     * Метод валидации даты рождения.
     * Возвращает [ValidationResult]
     */
    fun validateBirthday(birthday: LocalDate?): ValidationResult

}
