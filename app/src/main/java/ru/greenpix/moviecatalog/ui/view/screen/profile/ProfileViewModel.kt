package ru.greenpix.moviecatalog.ui.view.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.domain.ProfileModel
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.repository.UserRepository
import ru.greenpix.moviecatalog.ui.view.shared.model.ViewState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProfileViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    // TODO ОБЯЗАТЕЛЬНО УБРАТЬ И ВЫНЕСТИ
    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    }

    private val _loadState = mutableStateOf(ViewState.UNLOADED)
    private val _loginState = mutableStateOf("")
    private val _avatarUrlState = mutableStateOf("")
    private val _emailState = mutableStateOf("")
    private val _nameState = mutableStateOf("")
    private val _birthdayState = mutableStateOf<LocalDate>(LocalDate.MIN)
    private val _genderState = mutableStateOf(Gender.NONE)
    private val _canSaveState = mutableStateOf(false)

    private var userId: String = ""

    val loadState: State<ViewState>
        get() = _loadState
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
        if (loadState.value == ViewState.LOADED) {
            return
        }
        _loadState.value = ViewState.LOADING

        val profile = userRepository.getProfile()
        userId = profile.id
        _loginState.value = profile.username
        _emailState.value = profile.email
        _nameState.value = profile.name
        _birthdayState.value = LocalDateTime
            .parse(profile.birthday)
            .toLocalDate()
        _genderState.value = Gender.values()[profile.gender + 1]

        validate()
        _loadState.value = ViewState.LOADED
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

    fun onSave(
        onSuccess: () -> Unit,
    ) {
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
                onSuccess.invoke()
            }
            catch (e: AuthorizationException) {
                // TODO перенаправлять на авторизацию
            }
            catch (e: Exception) {
                e.printStackTrace()
                // TODO возможно сделать более широкую обработку ошибок
            }
        }
    }

    fun onLogout(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                authenticationRepository.logout()
                onSuccess.invoke()
            }
            catch (e: Exception) {
                e.printStackTrace()
                // TODO возможно сделать более широкую обработку ошибок
            }
        }
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSaveState.value = loginState.value.isNotBlank()
                && emailState.value.isNotBlank()
                && nameState.value.isNotBlank()
                && genderState.value != Gender.NONE
    }
}