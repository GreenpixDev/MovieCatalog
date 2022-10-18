package ru.greenpix.moviecatalog.screen.auth.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.screen.shared.StyledButton
import ru.greenpix.moviecatalog.screen.shared.StyledClickableText
import ru.greenpix.moviecatalog.screen.shared.StyledTextField
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(
    router: Router = Router()
) {
    val focusManager = LocalFocusManager.current

    // TODO интегрировать с ViewModel
    var login by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var password by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    val canSignIn = login.isNotBlank() && password.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // Поле ввода "Логин"
        StyledTextField(
            value = login,
            onValueChange = { login = it },
            placeholderText = stringResource(R.string.login)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Поле ввода "Пароль"
        StyledTextField(
            value = password,
            onValueChange = { password = it },
            placeholderText = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(1f))
        // Кнопка "Войти"
        StyledButton(
            // TODO интегрировать с ViewModel
            onClick = { router.routeTo(Screen.Home) },
            enabled = canSignIn,
            text = stringResource(R.string.sign_in)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Кликабельный текст "Регистрация"
        StyledClickableText(
            onClick = { router.routeTo(Screen.Auth.SignUp) },
            text = stringResource(R.string.registration)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SignInScreen()
        }
    }
}