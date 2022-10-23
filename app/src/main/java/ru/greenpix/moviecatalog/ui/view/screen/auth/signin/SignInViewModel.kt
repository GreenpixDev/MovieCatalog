package ru.greenpix.moviecatalog.ui.view.screen.auth.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greenpix.moviecatalog.repository.AuthenticateRepository

class SignInViewModel(
    private val authenticateRepository: AuthenticateRepository
) : ViewModel() {

    private val _loginState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")
    private val _canSignInState = mutableStateOf(false)

    val loginState: State<String>
        get() = _loginState
    val passwordState: State<String>
        get() = _passwordState
    val canSignInState: State<Boolean>
        get() = _canSignInState

    fun ifAuthenticated(onAuthenticated: () -> Unit) {
        if (authenticateRepository.isAuthenticated()) {
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

    fun trySignIn(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val login = loginState.value
        val password = passwordState.value

        viewModelScope.launch {
            try {
                authenticateRepository.login(
                    login = login,
                    password = password
                )
                onSuccess.invoke()
            } catch (_: Exception) { // TODO возможно сделать более широкую обработку ошибок
                onError.invoke()
            }
        }
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSignInState.value = loginState.value.isNotBlank()
                && passwordState.value.isNotBlank()
    }
}