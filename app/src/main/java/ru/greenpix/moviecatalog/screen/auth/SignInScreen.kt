package ru.greenpix.moviecatalog.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(
    router: Router = Router()
) {
    // TODO интегрировать с ViewModel
    var login by remember { mutableStateOf("") }
    // TODO интегрировать с ViewModel
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        TextField(
            value = login,
            onValueChange = { login = it },
            placeholder = { Text(stringResource(R.string.login)) },
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(stringResource(R.string.password)) },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            // TODO интегрировать с ViewModel
            onClick = { router.routeTo(Screen.Home) },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Text(stringResource(R.string.sign_in))
        }
        Button(
            // TODO интегрировать с ViewModel
            onClick = { router.routeTo(Screen.Auth.SignUp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Text(stringResource(R.string.go_to_sign_up))
        }
    }
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