package ru.greenpix.moviecatalog.ui.screen.signin.model

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class SignInViewState {
    object Default : SignInViewState()
    object SignInSuccessful : SignInViewState()

    sealed class Error(@StringRes val id: Int) : SignInViewState()
    object SignInFailed : Error(R.string.login_failed)
    object NetworkError : Error(R.string.network_error)
    object HttpError : Error(R.string.http_error)
    object UnknownError : Error(R.string.unknown_error)
}