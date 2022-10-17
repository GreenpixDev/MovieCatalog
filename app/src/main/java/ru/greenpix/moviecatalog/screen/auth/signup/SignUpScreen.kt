package ru.greenpix.moviecatalog.screen.auth.signup

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.util.compose.noRippleClickable
import ru.greenpix.moviecatalog.util.compose.roundedAtEnd
import ru.greenpix.moviecatalog.util.compose.roundedAtStart
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
    val now = LocalDate.now()
    var date by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth.$month.$year"
        },
        now.year,
        now.monthValue,
        now.dayOfMonth
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(Shapes.small)
            .border(1.dp, Gray, Shapes.small)
            .noRippleClickable { datePickerDialog.show() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = date.ifBlank { stringResource(R.string.birthday) },
            style = BodySmall,
            color = if (date.isBlank()) GrayFaded else Accent,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun GenderFieldView() {
    // TODO интегрировать с ViewModel
    var isMale by remember { mutableStateOf(false) }
    // TODO интегрировать с ViewModel
    var isFemale by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        // Мужской
        Button(
            onClick = {
                isMale = true
                isFemale = false
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isMale) Accent else Color.Transparent,
                contentColor = if (isMale) BaseWhite else GrayFaded,
            ),
            shape = Shapes.small.roundedAtStart(),
            border = BorderStroke(1.dp, Gray)
        ) {
            Text(
                text = stringResource(R.string.male),
                style = BodySmall,
            )
        }
        // Женский
        Button(
            onClick = {
                isFemale = true
                isMale = false
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isFemale) Accent else Color.Transparent,
                contentColor = if (isFemale) BaseWhite else GrayFaded,
            ),
            shape = Shapes.small.roundedAtEnd(),
            border = BorderStroke(1.dp, Gray)
        ) {
            Text(
                text = stringResource(R.string.female),
                style = BodySmall
            )
        }
    }
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