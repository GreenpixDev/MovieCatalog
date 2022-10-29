package ru.greenpix.moviecatalog.ui.view.screen.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.exception.AuthenticationException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import java.net.SocketException
import java.net.UnknownHostException

class SignInViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _viewState = mutableStateOf<SignInViewState>(SignInViewState.Default)
    private val _loginState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")
    private val _canSignInState = mutableStateOf(false)

    val viewState: State<SignInViewState>
        get() = _viewState
    val loginState: State<String>
        get() = _loginState
    val passwordState: State<String>
        get() = _passwordState
    val canSignInState: State<Boolean>
        get() = _canSignInState

    fun ifAuthenticated(onAuthenticated: () -> Unit) {
        if (authenticationRepository.isAuthenticated()) {
            onAuthenticated.invoke()
        }
    }

    fun onLoginChange(login: String) {
        _loginState.value = login
        validate()
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
        validate()
    }

    fun onSignIn(
        onSuccess: () -> Unit
    ) {
        val login = loginState.value
        val password = passwordState.value

        viewModelScope.launch {
            try {
                authenticationRepository.login(
                    login = login,
                    password = password
                )
                _viewState.value = SignInViewState.SignInSuccessful
                onSuccess.invoke()
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is AuthenticationException -> SignInViewState.SignInFailed
                    is HttpException -> SignInViewState.HttpError
                    is UnknownHostException, is SocketException -> SignInViewState.NetworkError
                    else -> SignInViewState.UnknownError
                }
            }
        }
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSignInState.value = loginState.value.isNotBlank()
                && passwordState.value.isNotBlank()
    }
}