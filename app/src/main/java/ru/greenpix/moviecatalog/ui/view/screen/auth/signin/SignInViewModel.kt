package ru.greenpix.moviecatalog.ui.view.screen.auth.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private val _loginState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")
    private val _canSignInState = mutableStateOf(false)

    val loginState: State<String>
        get() = _loginState
    val passwordState: State<String>
        get() = _passwordState
    val canSignInState: State<Boolean>
        get() = _canSignInState

    fun onLoginChange(login: String) {
        _loginState.value = login
        validate()
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
        validate()
    }

    fun trySignIn(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // TODO входим с помощью репозитория
        onSuccess.invoke()
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSignInState.value = loginState.value.isNotBlank()
                && passwordState.value.isNotBlank()
    }
}