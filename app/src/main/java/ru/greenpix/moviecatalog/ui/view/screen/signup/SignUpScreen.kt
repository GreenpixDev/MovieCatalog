package ru.greenpix.moviecatalog.ui.view.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.H1
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.ui.view.screen.signup.model.SignUpViewState
import ru.greenpix.moviecatalog.ui.view.shared.*
import java.time.LocalDate

@Composable
fun SignUpScreen(
    onSuccessSignUp: () -> Unit,
    onDirectToSignIn: () -> Unit,
    viewModel: SignUpViewModel = getViewModel()
) {
    val viewState by remember { viewModel.viewState }
    val login by remember { viewModel.loginState }
    val email by remember { viewModel.emailState }
    val name by remember { viewModel.nameState }
    val password by remember { viewModel.passwordState }
    val repeatPassword by remember { viewModel.repeatPasswordState }
    val birthday by remember { viewModel.birthdayState }
    val gender by remember { viewModel.genderState }
    val canSignUp by remember { viewModel.canSignUpState }

    SignUpContent(
        viewState = viewState,
        login = login,
        email = email,
        name = name,
        password = password,
        repeatPassword = repeatPassword,
        birthday = birthday,
        gender = gender,
        canSignUp = canSignUp,
        onLoginChange = viewModel::onLoginChange,
        onEmailChange = viewModel::onEmailChange,
        onNameChange = viewModel::onNameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onBirthdayChange = viewModel::onBirthdayChange,
        onGenderChange = viewModel::onGenderChange,
        onSignUpClick = viewModel::onSignUp,
        onGoToSignInClick = onDirectToSignIn
    )

    LaunchedEffect(key1 = viewState) {
        if (viewState is SignUpViewState.SignUpSuccessful) {
            onSuccessSignUp.invoke()
        }
    }
}

@Composable
private fun SignUpContent(
    viewState: SignUpViewState,
    login: String,
    email: String,
    name: String,
    password: String,
    repeatPassword: String,
    birthday: LocalDate?,
    gender: Gender,
    canSignUp: Boolean,
    onLoginChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onBirthdayChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onSignUpClick: () -> Unit,
    onGoToSignInClick: () -> Unit
) {
    MovieCatalogLogo(
        scaled = false,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 32.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 148.dp)
            .statusBarsPadding(),
    ) {
        Text(
            text = stringResource(R.string.registration),
            style = H1,
            color = Accent
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignUpFieldsView(
            login = login,
            email = email,
            name = name,
            password = password,
            repeatPassword = repeatPassword,
            birthday = birthday,
            gender = gender,
            onLoginChange = onLoginChange,
            onEmailChange = onEmailChange,
            onNameChange = onNameChange,
            onPasswordChange = onPasswordChange,
            onRepeatPasswordChange = onRepeatPasswordChange,
            onBirthdayChange = onBirthdayChange,
            onGenderChange = onGenderChange
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledErrorText(
            visible = viewState is SignUpViewState.Error,
            text = if (viewState is SignUpViewState.Error) {
                stringResource(id = viewState.id)
            } else {
                ""
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledButton(
            onClick = onSignUpClick,
            enabled = canSignUp,
            text = stringResource(R.string.sign_up)
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledClickableText(
            onClick = onGoToSignInClick,
            text = stringResource(R.string.have_account)
        )
    }
}

@Composable
private fun ColumnScope.SignUpFieldsView(
    login: String,
    email: String,
    name: String,
    password: String,
    repeatPassword: String,
    birthday: LocalDate?,
    gender: Gender,
    onLoginChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onBirthdayChange: (LocalDate) -> Unit,
    onGenderChange: (Gender) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.weight(1f)
    ) {
        item {
            StyledTextField(
                value = login,
                onValueChange = onLoginChange,
                placeholderText = stringResource(R.string.login),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            StyledTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholderText = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            StyledTextField(
                value = name,
                onValueChange = onNameChange,
                placeholderText = stringResource(R.string.name),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            StyledTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholderText = stringResource(R.string.password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        item {
            StyledTextField(
                value = repeatPassword,
                onValueChange = onRepeatPasswordChange,
                placeholderText = stringResource(R.string.repeat_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        item {
            StyledDateField(
                value = birthday,
                onValueChange = onBirthdayChange
            )
        }
        item {
            StyledGenderField(
                value = gender,
                onValueChange = onGenderChange
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
            SignUpContent(
                viewState = SignUpViewState.Default,
                login = "",
                email = "",
                name = "",
                password = "",
                repeatPassword = "",
                birthday = null,
                gender = Gender.NONE,
                canSignUp = false,
                onLoginChange = {},
                onEmailChange = {},
                onNameChange = {},
                onPasswordChange = {},
                onRepeatPasswordChange = {},
                onBirthdayChange = {},
                onGenderChange = {},
                onSignUpClick = {},
                onGoToSignInClick = {}
            )
        }
    }
}