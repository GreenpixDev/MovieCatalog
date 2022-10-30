package ru.greenpix.moviecatalog.ui.view.screen.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.exception.DuplicateUserNameException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.usecase.ValidationUseCase
import ru.greenpix.moviecatalog.usecase.model.ValidationResult
import java.net.SocketException
import java.net.UnknownHostException
import java.time.LocalDate

class SignUpViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val validationUseCase: ValidationUseCase
) : ViewModel() {

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

        if (validationUseCase.validateLogin(loginState.value) is ValidationResult.Failed) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.InvalidLogin
            return
        }

        if (validationUseCase.validateEmail(emailState.value) is ValidationResult.Failed) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.InvalidEmail
            return
        }

        val viewState: SignUpViewState? = when (validationUseCase.validatePassword(passwordState.value)) {
            is ValidationResult.Failed.Password.MinLength -> SignUpViewState.PasswordLengthLimit
            else -> null
        }
        if (viewState != null) {
            _canSignUpState.value = false
            _viewState.value = viewState
        }

        if (passwordState.value != repeatPasswordState.value) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.PasswordsNotMatch
            return
        }

        if (validationUseCase.validateBirthday(birthdayState.value) is ValidationResult.Failed) {
            _canSignUpState.value = false
            _viewState.value = SignUpViewState.InvalidBirthday
            return
        }
    }
}