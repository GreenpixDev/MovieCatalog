package ru.greenpix.moviecatalog.usecase

import ru.greenpix.moviecatalog.usecase.model.ValidationResult
import java.time.LocalDate

interface ValidationUseCase {

    fun validateLogin(login: String): ValidationResult

    fun validatePassword(password: String): ValidationResult

    fun validateEmail(email: String): ValidationResult

    fun validateUrl(url: String): ValidationResult

    fun validateBirthday(birthday: LocalDate?): ValidationResult

}
