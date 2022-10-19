package ru.greenpix.moviecatalog.ui.view.screen.auth.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.navigation.Router
import ru.greenpix.moviecatalog.ui.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.H1
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.ui.view.shared.*
import java.time.LocalDate

@Composable
fun SignUpScreen(
    router: Router = Router()
) {

    // TODO интегрировать с ViewModel
    val canSignIn = true

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        // Заголовок
        Text(
            text = stringResource(R.string.registration),
            style = H1,
            color = Accent
        )
        // Поля для регистрации
        Spacer(modifier = Modifier.height(16.dp))
        SignUpFieldsView()
        Spacer(modifier = Modifier.height(32.dp))
        // Кнопка "Зарегистрироваться"
        StyledButton(
            // TODO интегрировать с ViewModel
            onClick = { router.routeTo(Screen.Home) },
            enabled = canSignIn,
            text = stringResource(R.string.sign_up)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Кликабельный текст "У меня уже есть аккаунт"
        StyledClickableText(
            onClick = { router.routeTo(Screen.Auth.SignIn) },
            text = stringResource(R.string.have_account)
        )
    }
}

@Composable
private fun ColumnScope.SignUpFieldsView() {
    val focusManager = LocalFocusManager.current

    // TODO интегрировать с ViewModel
    var login by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var email by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var name by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var password by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var repeatPassword by remember { mutableStateOf("") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.weight(1f)
    ) {
        // Поле ввода "Логин"
        item {
            StyledTextField(
                value = login,
                onValueChange = { login = it },
                placeholderText = stringResource(R.string.login)
            )
        }
        // Поле ввода "Email"
        item {
            StyledTextField(
                value = email,
                onValueChange = { email = it },
                placeholderText = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
        }
        // Поле ввода "Имя"
        item {
            StyledTextField(
                value = name,
                onValueChange = { name = it },
                placeholderText = stringResource(R.string.name)
            )
        }
        // Поле ввода "Пароль"
        item {
            StyledTextField(
                value = password,
                onValueChange = { password = it },
                placeholderText = stringResource(R.string.password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        // Поле ввода "Повтор пароля"
        item {
            StyledTextField(
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                placeholderText = stringResource(R.string.repeat_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        // Поле ввода "Дата рождения"
        item {
            BirthdayFieldView()
        }
        // Поле ввода "Пол"
        item {
            GenderFieldView()
        }
    }
}

@Composable
private fun BirthdayFieldView() {
    // TODO интегрировать с ViewModel
    var date by remember { mutableStateOf<LocalDate?>(null) }

    StyledDateField(
        value = date,
        onValueChange = { date = it }
    )
}

@Composable
private fun GenderFieldView() {
    // TODO интегрировать с ViewModel
    var isMale by remember { mutableStateOf<Boolean?>(null) }

    StyledGenderField(
        value = isMale,
        onValueChange = { isMale = it }
    )
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SignUpScreen()
        }
    }
}