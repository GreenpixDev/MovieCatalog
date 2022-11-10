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
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.greenpix.moviecatalog.di.*
import ru.greenpix.moviecatalog.repository.AuthenticationRepository
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.navigation.view.RootNavHost
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(
                applicationModule,
                viewModelModule,
                repositoryModule,
                retrofitModule,
                useCaseModule,
                mapperModule
            )
        }

        // TODO переделать без deprecated
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val authenticated = get<AuthenticationRepository>().isAuthenticated()

        setContent {
            MovieCatalogTheme {
                ActivityScreen(
                    authenticated = authenticated
                )
            }
        }
    }
}

@Composable
private fun ActivityScreen(
    authenticated: Boolean
) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        RootNavHost(
            navController = navController,
            startDestination = if (authenticated) {
                Destination.Main.route
            } else {
                Destination.Auth.route
            }
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
            ActivityScreen(
                authenticated = false
            )
        }
    }
}