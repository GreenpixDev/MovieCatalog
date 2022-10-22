package ru.greenpix.moviecatalog

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.greenpix.moviecatalog.di.appModule
import ru.greenpix.moviecatalog.ui.navigation.Router
import ru.greenpix.moviecatalog.ui.navigation.RouterHost
import ru.greenpix.moviecatalog.ui.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.ui.view.screen.auth.AuthScreen
import ru.greenpix.moviecatalog.ui.view.screen.home.HomeScreen
import ru.greenpix.moviecatalog.ui.view.screen.movie.MovieScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }

        // TODO переделать без deprecated
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

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