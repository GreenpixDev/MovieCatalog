package ru.greenpix.moviecatalog.ui.view.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.view.screen.profile.model.ProfileViewState
import ru.greenpix.moviecatalog.ui.view.shared.*
import java.time.LocalDate

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onDirectToAuth: () -> Unit,
    viewModel: ProfileViewModel = getViewModel()
) {
    var reconnectCount by remember { mutableStateOf(0) }
    val viewState = remember { viewModel.viewState }.value

    when {
        viewState is ProfileViewState.Loading -> LoadingScreen()
        viewState is ProfileViewState.Error && viewState.isLoadingError -> ErrorScreen(
            text = stringResource(id = viewState.id),
            onRetry = { reconnectCount++ }
        )
        else -> {
            val login by remember { viewModel.loginState }
            val email by remember { viewModel.emailState }
            val avatarUrl by remember { viewModel.avatarUrlState }
            val name by remember { viewModel.nameState }
            val birthday by remember { viewModel.birthdayState }
            val gender by remember { viewModel.genderState }
            val canSave by remember { viewModel.canSaveState }

            ProfileContent(
                viewState = viewState,
                login = login,
                email = email,
                avatarUrl = avatarUrl,
                name = name,
                birthday = birthday,
                gender = gender,
                canSave = canSave,
                onEmailChange = viewModel::onEmailChange,
                onAvatarUrlChange = viewModel::onAvatarUrlChange,
                onNameChange = viewModel::onNameChange,
                onBirthdayChange = viewModel::onBirthdayChange,
                onGenderChange = viewModel::onGenderChange,
                onSave = viewModel::onSave,
                onLogout = viewModel::onLogout
            )
        }
    }

    LaunchedEffect(key1 = reconnectCount, block = {
        viewModel.load()
    })

    LaunchedEffect(key1 = viewState, block = {
        when (viewState) {
            is ProfileViewState.AuthorizationFailed,
            is ProfileViewState.LogoutSuccessful -> onDirectToAuth.invoke()
            is ProfileViewState.SaveSuccessful -> onBack.invoke()
            else -> {}
        }
    })
}

@Composable
fun ProfileContent(
    viewState: ProfileViewState,
    login: String,
    email: String,
    avatarUrl: String,
    name: String,
    birthday: LocalDate,
    gender: Gender,
    canSave: Boolean,
    onEmailChange: (String) -> Unit,
    onAvatarUrlChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onBirthdayChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onSave: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .statusBarsPadding(),
    ) {
        ProfileHeaderView(
            login = login,
            avatarUrl = avatarUrl
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileFieldsView(
            email = email,
            avatarUrl = avatarUrl,
            name = name,
            birthday = birthday,
            gender = gender,
            onEmailChange = onEmailChange,
            onAvatarUrlChange = onAvatarUrlChange,
            onNameChange = onNameChange,
            onBirthdayChange = onBirthdayChange,
            onGenderChange = onGenderChange
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledErrorText(
            visible = viewState is ProfileViewState.Error && !viewState.isLoadingError,
            text = if (viewState is ProfileViewState.Error) {
                stringResource(id = viewState.id)
            } else {
                ""
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledButton(
            onClick = onSave,
            enabled = canSave,
            text = stringResource(R.string.save)
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledClickableText(
            onClick = onLogout,
            text = stringResource(R.string.logout)
        )
    }
}

@Composable
private fun ProfileHeaderView(
    login: String,
    avatarUrl: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Avatar(
            url = avatarUrl,
            modifier = Modifier.size(88.dp)
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
private fun ColumnScope.ProfileFieldsView(
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
            FieldView(stringResource(R.string.email)) {
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
            FieldView(stringResource(R.string.avatar_url)) {
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
            FieldView(stringResource(R.string.name)) {
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
            FieldView(stringResource(R.string.birthday)) {
                StyledDateField(
                    value = birthday,
                    onValueChange = onBirthdayChange
                )
            }
        }
        item {
            FieldView(stringResource(R.string.gender)) {
                StyledGenderField(
                    value = gender,
                    onValueChange = onGenderChange
                )
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

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProfileContent(
                viewState = ProfileViewState.Default,
                login = "Test",
                email = "",
                avatarUrl = "",
                name = "",
                birthday = LocalDate.MIN,
                gender = Gender.FEMALE,
                canSave = false,
                onEmailChange = {},
                onAvatarUrlChange = {},
                onNameChange = {},
                onBirthdayChange = {},
                onGenderChange = {},
                onSave = {},
                onLogout = {}
            )
        }
    }
}