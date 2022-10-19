package ru.greenpix.moviecatalog.ui.navigation

sealed class Screen(
    val route: String
) {

    object Auth: Screen("auth") {
        object SignIn: Screen("auth/sign-in")
        object SignUp: Screen("auth/sign-up")
    }
    object Home: Screen("home") {
        object Main: Screen("home/main")
        object Profile: Screen("home/profile")
    }
    object Movie: Screen("movie")

}