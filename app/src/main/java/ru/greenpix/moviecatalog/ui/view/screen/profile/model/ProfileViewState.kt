package ru.greenpix.moviecatalog.ui.view.screen.profile.model

import androidx.annotation.StringRes
import ru.greenpix.moviecatalog.R

sealed class ProfileViewState {
    object Loading: ProfileViewState()
    object Default: ProfileViewState()
    object SaveSuccessful: ProfileViewState()
    object LogoutSuccessful: ProfileViewState()
    object AuthorizationFailed: ProfileViewState()

    sealed class Error(@StringRes val id: Int, val isLoadingError: Boolean) : ProfileViewState()
    class NetworkError(isLoadingError: Boolean) : Error(R.string.network_error, isLoadingError)
    class HttpError(isLoadingError: Boolean) : Error(R.string.http_error, isLoadingError)
    class UnknownError(isLoadingError: Boolean) : Error(R.string.unknown_error, isLoadingError)

    sealed class ValidateError(@StringRes id: Int) : Error(id, false)
    object InvalidUrl : ValidateError(R.string.invalid_url)
    object InvalidEmail : ValidateError(R.string.invalid_email)
    object InvalidBirthday : ValidateError(R.string.invalid_birthday)
}