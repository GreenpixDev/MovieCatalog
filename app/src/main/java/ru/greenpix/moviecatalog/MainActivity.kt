package ru.greenpix.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.RouterHost
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.screen.AuthScreen
import ru.greenpix.moviecatalog.screen.HomeScreen
import ru.greenpix.moviecatalog.screen.MovieScreen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                ActivityScreen()
            }
        }
    }
}

@Composable
private fun ActivityScreen() {
    val router = Router()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        RouterHost(
            router = router,
            startDestination = Screen.Auth,
            screens = mapOf(
                Screen.Auth to { AuthScreen(it) },
                Screen.Home to { HomeScreen(it) },
                Screen.Movie to { MovieScreen(it) }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ActivityScreen()
        }
    }
}