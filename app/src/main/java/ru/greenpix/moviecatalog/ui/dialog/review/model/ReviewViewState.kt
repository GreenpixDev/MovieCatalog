package ru.greenpix.moviecatalog.ui.dialog.review.model

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class ReviewViewState {
    object Default : ReviewViewState()
    object AuthorizationFailed : ReviewViewState()
    object Saved : ReviewViewState()

    sealed class Error(@StringRes val id: Int) : ReviewViewState()
    object NetworkError : Error(R.string.network_error)
    object HttpError : Error(R.string.http_error)
    object UnknownError : Error(R.string.unknown_error)
}