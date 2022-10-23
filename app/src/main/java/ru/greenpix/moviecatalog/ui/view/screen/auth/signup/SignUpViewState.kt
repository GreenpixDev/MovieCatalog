package ru.greenpix.moviecatalog.ui.view.screen.auth.signup

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class SignUpViewState {
    object Default : SignUpViewState()
    object SignUpSuccessful : SignUpViewState()

    sealed class Error(@StringRes val id: Int) : SignUpViewState()
    object DuplicateUserName : Error(R.string.duplicate_username)
    object NetworkError : Error(R.string.network_error)
    object HttpError : Error(R.string.http_error)
    object UnknownError : Error(R.string.unknown_error)

    sealed class ValidateError(@StringRes id: Int) : Error(id)
    object PasswordsNotMatch : ValidateError(R.string.passwords_not_match)
    object PasswordLengthLimit : ValidateError(R.string.password_length_limit)
    object InvalidEmail : ValidateError(R.string.invalid_email)
    object InvalidBirthday : ValidateError(R.string.invalid_birthday)
}