package ru.greenpix.moviecatalog.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.ui.screen.profile.model.ProfileViewState
import ru.greenpix.moviecatalog.ui.screen.profile.view.ProfileFields
import ru.greenpix.moviecatalog.ui.screen.profile.view.ProfileHeader
import ru.greenpix.moviecatalog.ui.shared.view.*
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
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
        ProfileHeader(
            login = login,
            avatarUrl = avatarUrl
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileFields(
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