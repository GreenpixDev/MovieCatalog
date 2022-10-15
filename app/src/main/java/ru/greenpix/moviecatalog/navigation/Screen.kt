package ru.greenpix.moviecatalog.navigation

import androidx.compose.runtime.Composable
import ru.greenpix.moviecatalog.screen.AuthScreen
import ru.greenpix.moviecatalog.screen.HomeScreen
import ru.greenpix.moviecatalog.screen.MovieScreen
import ru.greenpix.moviecatalog.screen.auth.SignInScreen
import ru.greenpix.moviecatalog.screen.auth.SignUpScreen
import ru.greenpix.moviecatalog.screen.home.MainScreen
import ru.greenpix.moviecatalog.screen.home.ProfileScreen

sealed class Screen(
    val route: String,
    val composable: @Composable (Router) -> Unit
) {

    object Auth: Screen("auth", { AuthScreen(it) }) {
        object SignIn: Screen("auth/sign-in", { SignInScreen(it) })
        object SignUp: Screen("auth/sign-up", { SignUpScreen(it) })
    }
    object Home: Screen("home", { HomeScreen(it) }) {
        object Main: Screen("home/main", { MainScreen(it) })
        object Profile: Screen("home/profile", { ProfileScreen(it) })
    }
    object Movie: Screen("movie", { MovieScreen(it) })

}