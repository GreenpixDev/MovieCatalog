package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.greenpix.moviecatalog.ui.navigation.Destination

@Composable
fun RootNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigationAuth(navController)
        navigationMain(navController)
        navigationMovie(navController)
        navigationReview(navController)
    }
}

fun NavController.navigateToAuth() {
    navigate(Destination.Auth.buildRoute()) {
        popUpTo(Destination.Main.route) {
            inclusive = true
        }
    }
}