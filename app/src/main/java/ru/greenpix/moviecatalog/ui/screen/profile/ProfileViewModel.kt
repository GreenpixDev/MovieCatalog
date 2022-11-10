package ru.greenpix.moviecatalog.ui.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.domain.ProfileModel
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.repository.UserRepository
import ru.greenpix.moviecatalog.ui.screen.profile.model.ProfileViewState
import ru.greenpix.moviecatalog.usecase.ValidationUseCase
import ru.greenpix.moviecatalog.usecase.model.ValidationResult
import java.net.SocketException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProfileViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val validationUseCase: ValidationUseCase
) : ViewModel() {

    // TODO ОБЯЗАТЕЛЬНО УБРАТЬ И ВЫНЕСТИ
    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    }

    private val _viewState = mutableStateOf<ProfileViewState>(ProfileViewState.Loading)
    private val _loginState = mutableStateOf("")
    private val _avatarUrlState = mutableStateOf("")
    private val _emailState = mutableStateOf("")
    private val _nameState = mutableStateOf("")
    private val _birthdayState = mutableStateOf<LocalDate>(LocalDate.MIN)
    private val _genderState = mutableStateOf(Gender.NONE)
    private val _canSaveState = mutableStateOf(false)

    private var userId: String = ""

    val viewState: State<ProfileViewState>
        get() = _viewState
    val loginState: State<String>
        get() = _loginState
    val avatarUrlState: State<String>
        get() = _avatarUrlState
    val emailState: State<String>
        get() = _emailState
    val nameState: State<String>
        get() = _nameState
    val birthdayState: State<LocalDate>
        get() = _birthdayState
    val genderState: State<Gender>
        get() = _genderState
    val canSaveState: State<Boolean>
        get() = _canSaveState

    suspend fun load() {
        if (viewState.value is ProfileViewState.Default || viewState.value is ProfileViewState.ValidateError) {
            return
        }
        _viewState.value = ProfileViewState.Loading

        try {
            val profile = userRepository.getProfile()
            userId = profile.id
            _loginState.value = profile.username
            _emailState.value = profile.email
            _nameState.value = profile.name
            _birthdayState.value = LocalDateTime
                .parse(profile.birthday)
                .toLocalDate()
            _genderState.value = Gender.values()[profile.gender + 1]

            _viewState.value = ProfileViewState.Default
        }
        catch (e: AuthorizationException) {
            _viewState.value = ProfileViewState.AuthorizationFailed
        }
        catch (e: Exception) {
            _viewState.value = when(e) {
                is HttpException -> ProfileViewState.HttpError(true)
                is UnknownHostException, is SocketException -> ProfileViewState.NetworkError(true)
                else -> {
                    e.printStackTrace()
                    ProfileViewState.UnknownError(true)
                }
            }
        }
    }

    fun onAvatarUrlChange(avatarUrl: String) {
        _avatarUrlState.value = avatarUrl
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

    fun onBirthdayChange(birthday: LocalDate) {
        _birthdayState.value = birthday
        validate()
    }

    fun onGenderChange(gender: Gender) {
        _genderState.value = gender
        validate()
    }

    fun onSave() {
        val login = loginState.value
        val email = emailState.value
        val avatarUrl = avatarUrlState.value
        val name = nameState.value
        val birthday = birthdayState.value
        val gender = genderState.value

        viewModelScope.launch {
            try {
                userRepository.updateProfile(ProfileModel(
                    id = userId,
                    username = login,
                    avatarLink = avatarUrl,
                    email = email,
                    name = name,
                    birthday = birthday.atStartOfDay().format(FORMATTER),
                    gender = gender.ordinal - 1
                ))
                _viewState.value = ProfileViewState.SaveSuccessful
            }
            catch (e: AuthorizationException) {
                _viewState.value = ProfileViewState.AuthorizationFailed
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is HttpException -> ProfileViewState.HttpError(false)
                    is UnknownHostException, is SocketException -> ProfileViewState.NetworkError(false)
                    else -> {
                        e.printStackTrace()
                        ProfileViewState.UnknownError(false)
                    }
                }
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            try {
                authenticationRepository.logout()
                _viewState.value = ProfileViewState.LogoutSuccessful
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is HttpException -> ProfileViewState.HttpError(false)
                    is UnknownHostException, is SocketException -> ProfileViewState.NetworkError(false)
                    else -> {
                        e.printStackTrace()
                        ProfileViewState.UnknownError(false)
                    }
                }
            }
        }
    }

    private fun validate() {
        if (viewState.value is ProfileViewState.ValidateError) {
            _viewState.value = ProfileViewState.Default
        }

        _canSaveState.value = loginState.value.isNotBlank()
                && emailState.value.isNotBlank()
                && nameState.value.isNotBlank()
                && genderState.value != Gender.NONE
        if (!_canSaveState.value) return

        if (validationUseCase.validateEmail(emailState.value) is ValidationResult.Failed) {
            _canSaveState.value = false
            _viewState.value = ProfileViewState.InvalidEmail
            return
        }

        if (avatarUrlState.value.isNotBlank()
            && validationUseCase.validateUrl(avatarUrlState.value) is ValidationResult.Failed) {
            _canSaveState.value = false
            _viewState.value = ProfileViewState.InvalidUrl
            return
        }

        if (validationUseCase.validateBirthday(birthdayState.value) is ValidationResult.Failed) {
            _canSaveState.value = false
            _viewState.value = ProfileViewState.InvalidBirthday
            return
        }
    }
}