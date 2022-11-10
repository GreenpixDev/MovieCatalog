package ru.greenpix.moviecatalog.ui.screen.main.model

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class MainViewState {
    object Loading : MainViewState()
    object Default : MainViewState()
    object AuthorizationFailed : MainViewState()

    sealed class Error(@StringRes val id: Int) : MainViewState()
    object NetworkError : Error(R.string.network_error)
    object HttpError : Error(R.string.http_error)
    object UnknownError : Error(R.string.unknown_error)
}