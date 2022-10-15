package ru.greenpix.moviecatalog.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.RouterHost
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun AuthScreen(
    router: Router = Router()
) {
    Column {
        Text(text = "Auth")
        RouterHost(
            router = router,
            startDestination = Screen.Auth.SignIn,
            screens = listOf(Screen.Auth.SignIn, Screen.Auth.SignUp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthPreview() {
    MovieCatalogTheme {
        AuthScreen()
    }
}