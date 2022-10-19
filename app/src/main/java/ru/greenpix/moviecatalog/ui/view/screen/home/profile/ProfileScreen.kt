package ru.greenpix.moviecatalog.ui.view.screen.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.navigation.Router
import ru.greenpix.moviecatalog.ui.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.view.shared.*
import java.time.LocalDate

@Composable
fun ProfileScreen(
    router: Router = Router()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .statusBarsPadding(),
    ) {
        // Заголовок
        // TODO получать логин из ViewModel
        ProfileHeaderView(login = "Тест")

        // Поля для регистрации
        Spacer(modifier = Modifier.height(16.dp))
        ProfileFieldsView()
        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка "Сохранить"
        StyledButton(
            // TODO интегрировать с ViewModel
            onClick = { router.routeTo(Screen.Home.Main) },
            text = stringResource(R.string.save)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Кликабельный текст "Выйти из аккаунта"
        StyledClickableText(
            onClick = { router.routeTo(Screen.Auth) },
            text = stringResource(R.string.logout)
        )
    }
}

@Composable
private fun ProfileHeaderView(
    login: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(88.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = login,
            style = H1,
            color = BrightWhite
        )
    }
}

@Composable
private fun ColumnScope.ProfileFieldsView() {
    // TODO интегрировать с ViewModel
    var email by remember { mutableStateOf("test@example.com") }
    // TODO интегрировать с ViewModel
    var avatarUrl by remember { mutableStateOf("https://vk.cc/chdMpX") }
    // TODO интегрировать с ViewModel
    var name by remember { mutableStateOf("Тест Тестович") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.weight(1f)
    ) {
        // Поле ввода "Email"
        item {
            FieldView(stringResource(R.string.email)) {
                StyledTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholderText = stringResource(R.string.example_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )
            }
        }

        // Поле ввода "Ссылка на аватарку"
        item {
            FieldView(stringResource(R.string.avatar_url)) {
                StyledTextField(
                    value = avatarUrl,
                    onValueChange = { avatarUrl = it },
                    placeholderText = stringResource(R.string.example_avatar_url),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                )
            }
        }

        // Поле ввода "Имя"
        item {
            FieldView(stringResource(R.string.name)) {
                StyledTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholderText = stringResource(R.string.example_name)
                )
            }
        }

        // Поле ввода "Дата рождения"
        item {
            FieldView(stringResource(R.string.birthday)) {
                BirthdayFieldView()
            }
        }

        // Поле ввода "Пол"
        item {
            FieldView(stringResource(R.string.gender)) {
               GenderFieldView()
            }
        }
    }
}

@Composable
private fun FieldView(
    text: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = text,
            style = Body,
            color = GraySilver,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content.invoke(this)
    }
}

@Composable
private fun BirthdayFieldView() {
    // TODO интегрировать с ViewModel
    var date by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    StyledDateField(
        value = date,
        onValueChange = { date = it }
    )
}

@Composable
private fun GenderFieldView() {
    // TODO интегрировать с ViewModel
    var isMale by remember { mutableStateOf<Boolean?>(true) }

    StyledGenderField(
        value = isMale,
        onValueChange = { isMale = it }
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProfileScreen()
        }
    }
}