package ru.greenpix.moviecatalog.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(
    router: Router = Router()
) {
    Column {
        Text(text = "SignIn")
        Button(onClick = { router.routeTo(Screen.Home) }) {
            Text(text = "Войти")
        }
        Button(onClick = {
            router.routeTo(Screen.Auth.SignUp)
        }) {
            Text(text = "Зарегистрироваться")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    MovieCatalogTheme {
        SignInScreen()
    }
}