package ru.greenpix.moviecatalog.ui.view.screen.home.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.ui.view.shared.model.ViewState
import java.time.LocalDate

class ProfileViewModel(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loadState = mutableStateOf(ViewState.UNLOADED)
    private val _loginState = mutableStateOf("")
    private val _avatarUrlState = mutableStateOf("")
    private val _emailState = mutableStateOf("")
    private val _nameState = mutableStateOf("")
    private val _birthdayState = mutableStateOf<LocalDate?>(null)
    private val _genderState = mutableStateOf(Gender.NONE)
    private val _canSaveState = mutableStateOf(false)

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
    val birthdayState: State<LocalDate?>
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
        // TODO получаем данные из репозитория (сейчас заглушка)
        delay(1000)
        _loginState.value = "Test"
        _emailState.value = "example@gmail.com"
        _nameState.value = "Роман"
        _birthdayState.value = LocalDate.now()
        _genderState.value = Gender.MALE
        validate()
        _loadState.value = ViewState.LOADED
    }

    fun onAvatarUrlChange(avatarUrl: String) {
        _loginState.value = avatarUrl
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

    fun onBirthdayChange(birthday: LocalDate?) {
        _birthdayState.value = birthday
        validate()
    }

    fun onGenderChange(gender: Gender) {
        _genderState.value = gender
        validate()
    }

    fun save(
        onSuccess: () -> Unit,
    ) {
        // TODO сохраняем с помощью репозитория
        onSuccess.invoke()
    }

    fun logout(
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                authenticationRepository.logout()
                onSuccess.invoke()
            } catch (_: Exception) { // TODO возможно сделать более широкую обработку ошибок
                onSuccess.invoke() // TODO пока заглушка
            }
        }
    }

    // TODO валидация в usecase
    private fun validate() {
        _canSaveState.value = loginState.value.isNotBlank()
                && emailState.value.isNotBlank()
                && nameState.value.isNotBlank()
                && birthdayState.value != null
                && genderState.value != Gender.NONE
    }
}