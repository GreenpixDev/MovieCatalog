package ru.greenpix.moviecatalog.ui.view.screen.auth.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.exception.DuplicateUserNameException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import java.net.SocketException
import java.net.UnknownHostException
import java.time.LocalDate

class SignUpViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _viewState = mutableStateOf<SignUpViewState>(SignUpViewState.Default)
    private val _loginState = mutableStateOf("")
    private val _emailState = mutableStateOf("")
    private val _nameState = mutableStateOf("")
    private val _passwordState = mutableStateOf("")
    private val _repeatPasswordState = mutableStateOf("")
    private val _birthdayState = mutableStateOf<LocalDate?>(null)
    private val _genderState = mutableStateOf(Gender.NONE)
    private val _canSignUpState = mutableStateOf(false)

    val viewState: State<SignUpViewState>
        get() = _viewState
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

    fun onSignUp(
        onSuccess: () -> Unit
    ) {
        val login = loginState.value
        val email = emailState.value
        val name = nameState.value
        val password = passwordState.value
        val birthday = birthdayState.value
        val gender = genderState.value

        checkNotNull(birthday) { "birthday cannot be null" }

        viewModelScope.launch {
            try {
                authenticationRepository.register(
                    login = login,
                    email = email,
                    name = name,
                    password = password,
                    birthday = birthday,
                    gender = gender
                )
                _viewState.value = SignUpViewState.SignUpSuccessful
                onSuccess.invoke()
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is DuplicateUserNameException -> SignUpViewState.DuplicateUserName
                    is HttpException -> SignUpViewState.HttpError
                    is UnknownHostException, is SocketException -> SignUpViewState.NetworkError
                    else -> SignUpViewState.UnknownError
                }
            }
        }
    }

    // TODO валидация в usecase
    private fun validate() {
        if (viewState.value is SignUpViewState.ValidateError) {
            _viewState.value = SignUpViewState.Default
        }

        _canSignUpState.value = loginState.value.isNotBlank()
                && emailState.value.isNotBlank()
                && nameState.value.isNotBlank()
                && passwordState.value.isNotBlank()
                && repeatPasswordState.value.isNotBlank()
                && birthdayState.value != null
                && genderState.value != Gender.NONE
        if (!_canSignUpState.value) return

        if (!emailState.value.contains("@")) { // TODO норм валидация email
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.InvalidEmail
            return
        }

        if (passwordState.value.length < MIN_PASSWORD_LENGTH) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.PasswordLengthLimit
            return
        }

        if (passwordState.value != repeatPasswordState.value) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.PasswordsNotMatch
            return
        }

        if (birthdayState.value?.isAfter(LocalDate.now()) == true) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.InvalidBirthday
            return
        }
    }
}