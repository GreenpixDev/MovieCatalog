package ru.greenpix.moviecatalog.ui.screen.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import ru.greenpix.moviecatalog.ui.screen.signin.model.SignInViewState
import ru.greenpix.moviecatalog.ui.shared.view.*
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(
    animateLogo: Boolean = true,
    onSuccessSignIn: () -> Unit,
    onDirectToSignUp: () -> Unit,
    viewModel: SignInViewModel = getViewModel()
) {
    val viewState by remember { viewModel.viewState }
    val login by remember { viewModel.loginState }
    val password by remember { viewModel.passwordState }
    val canSignIn by remember { viewModel.canSignInState }

    SignInContent(
        animateLogo = animateLogo,
        viewState = viewState,
        login = login,
        password = password,
        canSignIn = canSignIn,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = viewModel::onSignIn,
        onGoToSignUpClick = onDirectToSignUp
    )

    LaunchedEffect(key1 = viewState) {
        if (viewState is SignInViewState.SignInSuccessful) {
            onSuccessSignIn.invoke()
        }
    }
}

@Composable
private fun SignInContent(
    animateLogo: Boolean,
    viewState: SignInViewState,
    login: String,
    password: String,
    canSignIn: Boolean,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onGoToSignUpClick: () -> Unit
) {
    MovieCatalogLogo(
        animate = animateLogo,
        scaled = true,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 32.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 250.dp)
            .statusBarsPadding(),
    ) {
        StyledTextField(
            value = login,
            onValueChange = onLoginChange,
            placeholderText = stringResource(R.string.login),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        StyledTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholderText = stringResource(R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(1f))
        StyledErrorText(
            visible = viewState is SignInViewState.Error,
            text = if (viewState is SignInViewState.Error) {
                stringResource(id = viewState.id)
            } else {
                ""
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledButton(
            onClick = onSignInClick,
            enabled = canSignIn,
            text = stringResource(R.string.sign_in)
        )
        Spacer(modifier = Modifier.height(8.dp))
        StyledClickableText(
            onClick = onGoToSignUpClick,
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
            SignInContent(
                animateLogo = true,
                viewState = SignInViewState.Default,
                login = "",
                password = "",
                canSignIn = true,
                onLoginChange = {},
                onPasswordChange = {},
                onSignInClick = {},
                onGoToSignUpClick = {}
            )
        }
    }
}