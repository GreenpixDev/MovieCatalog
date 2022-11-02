package ru.greenpix.moviecatalog.ui.view.screen.movie.model

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class MovieViewState {
    object Loading : MovieViewState()
    object Default : MovieViewState()
    object AuthorizationFailed : MovieViewState()

    sealed class Error(@StringRes val id: Int) : MovieViewState()
    object NetworkError : Error(R.string.network_error)
    object HttpError : Error(R.string.http_error)
    object UnknownError : Error(R.string.unknown_error)
}
