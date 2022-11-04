package ru.greenpix.moviecatalog.usecase.impl

import android.webkit.URLUtil
import ru.greenpix.moviecatalog.usecase.ValidationUseCase
import ru.greenpix.moviecatalog.usecase.model.ValidationResult
import java.time.LocalDate

class ValidationUseCaseImpl : ValidationUseCase {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
        val LOGIN_REGEX = Regex("[a-zA-Z0-9._\\-]+")
        val EMAIL_REGEX = Regex("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$")
    }

    override fun validateLogin(login: String): ValidationResult {
        if (!login.matches(LOGIN_REGEX)) {
            return ValidationResult.Failed.Login
        }
        return ValidationResult.Successful
    }

    override fun validatePassword(password: String): ValidationResult {
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResult.Failed.Password.MinLength
        }
        return ValidationResult.Successful
    }

    override fun validateEmail(email: String): ValidationResult {
        if (!email.matches(EMAIL_REGEX)) {
            return ValidationResult.Failed.Email
        }
        return ValidationResult.Successful
    }

    override fun validateUrl(url: String): ValidationResult {
        if (!URLUtil.isValidUrl(url)) {
            return ValidationResult.Failed.Url
        }
        return ValidationResult.Successful
    }

    override fun validateBirthday(birthday: LocalDate?): ValidationResult {
        if (birthday?.isAfter(LocalDate.now()) == true) {
            return ValidationResult.Failed.Birthday
        }
        return ValidationResult.Successful
    }
}