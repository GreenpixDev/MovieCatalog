package ru.greenpix.moviecatalog.screen.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.util.noRippleClickable

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
            text = stringResource(R.string.go_to_sign_up)
        )
    }
}

@Composable
private fun StyledClickableText(
    onClick: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        Text(
            text = text,
            color = Accent,
            style = Body,
            modifier = Modifier
                .align(Alignment.Center)
                .noRippleClickable(onClick),
        )
    }
}

@Composable
private fun StyledButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Accent,
            contentColor = BrightWhite,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Accent
        ),
        shape = Shapes.medium,
        border = if (!enabled) BorderStroke(1.dp, Gray) else null
    ) {
        Text(
            text = text,
            style = Body
        )
    }
}

@Composable
private fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = BodySmall,
        placeholder = {
            Text(
                text = placeholderText,
                style = BodySmall,
                color = GrayFaded
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Accent,
            focusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.small)
            .border(1.dp, Gray, Shapes.small),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SignInScreen()
        }
    }
}