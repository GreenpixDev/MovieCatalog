package ru.greenpix.moviecatalog.ui.screen.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.ui.shared.view.StyledDateField
import ru.greenpix.moviecatalog.ui.shared.view.StyledGenderField
import ru.greenpix.moviecatalog.ui.shared.view.StyledTextField
import ru.greenpix.moviecatalog.ui.theme.Body
import ru.greenpix.moviecatalog.ui.theme.GraySilver
import java.time.LocalDate

@Composable
fun ColumnScope.ProfileFields(
    email: String,
    avatarUrl: String,
    name: String,
    birthday: LocalDate,
    gender: Gender,
    onEmailChange: (String) -> Unit,
    onAvatarUrlChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onBirthdayChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.weight(1f)
    ) {
        item {
            ProfileFieldContainer(stringResource(R.string.email)) {
                StyledTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholderText = stringResource(R.string.example_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                )
            }
        }
        item {
            ProfileFieldContainer(stringResource(R.string.avatar_url)) {
                StyledTextField(
                    value = avatarUrl,
                    onValueChange = onAvatarUrlChange,
                    placeholderText = stringResource(R.string.example_avatar_url),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                )
            }
        }
        item {
            ProfileFieldContainer(stringResource(R.string.name)) {
                StyledTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholderText = stringResource(R.string.example_name),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }
        item {
            ProfileFieldContainer(stringResource(R.string.birthday)) {
                StyledDateField(
                    value = birthday,
                    onValueChange = onBirthdayChange
                )
            }
        }
        item {
            ProfileFieldContainer(stringResource(R.string.gender)) {
                StyledGenderField(
                    value = gender,
                    onValueChange = onGenderChange
                )
            }
        }
    }
}

@Composable
private fun ProfileFieldContainer(
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