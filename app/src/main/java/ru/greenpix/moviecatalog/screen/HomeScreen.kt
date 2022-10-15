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
fun HomeScreen(
    router: Router = Router()
) {
    Column {
        Text(text = "Home")
        RouterHost(
            router = router,
            startDestination = Screen.Home.Main,
            screens = listOf(Screen.Home.Main, Screen.Home.Profile)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    MovieCatalogTheme {
        HomeScreen()
    }
}