package ru.greenpix.moviecatalog.ui.view.screen.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.greenpix.moviecatalog.ui.view.shared.model.Gender
import java.time.LocalDate

class SignUpViewModel : ViewModel() {

    private val _loginState = mutableStateOf("")
    private val _emailState = mutableStateOf("")
    private val _nameState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")
    private val _repeatPasswordState = mutableStateOf("")
    private val _birthdayState = mutableStateOf<LocalDate?>(null)
    private val _genderState = mutableStateOf(Gender.NONE)
    private val _canSignUpState = mutableStateOf(false)

    val loginState: State<String>
        get() = _loginState
    val emailState: State<String>
        get() = _emailState
    val nameState: State<String>
        get() = _nameState
    val passwordState: State<String>
        get() = _passwordState
    val repeatPasswordState: State<String>
        get() = _repeatPasswordState
    val birthdayState: State<LocalDate?>
        get() = _birthdayState
    val genderState: State<Gender>
        get() = _genderState
    val canSignUpState: State<Boolean>
        get() = _canSignUpState

    fun onLoginChange(login: String) {
        _loginState.value = login
        validate()
    }

    fun onEmailChange(email: String) {
        _emailState.value = email
        validate()
    }

    fun onNameChange(name: String) {
        _nameState.value = name
        validate()
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
        validate()
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        _repeatPasswordState.value = repeatPassword
        validate()
    }

    fun onBirthdayChange(birthday: LocalDate?) {
        _birthdayState.value = birthday
        validate()
    }

    fun onGenderChange(gender: Gender) {
        _genderState.value = gender
        validate()
    }

    fun trySignUp(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // TODO регистрируемся с помощью репозитория
        onSuccess.invoke()
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSignUpState.value = loginState.value.isNotBlank()
                && emailState.value.isNotBlank()
                && nameState.value.isNotBlank()
                && passwordState.value.isNotBlank()
                && repeatPasswordState.value.isNotBlank()
                && birthdayState.value != null
                && genderState.value != Gender.NONE
    }
}