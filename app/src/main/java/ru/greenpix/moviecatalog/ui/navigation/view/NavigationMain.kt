package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.screen.main.MainScreen
import ru.greenpix.moviecatalog.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.navigationMain(navController: NavController) {
    navigation(
        route = Destination.Main.route,
        startDestination = Destination.Main.Gallery.route
    ) {
        composable(route = Destination.Main.Gallery.route) {
            MainNavigationScaffold(navController = navController) {
                MainScreen(
                    onDirectToMovie = { movieId, isFavorite ->
                        navController.navigate(Destination.Movie.buildRoute(movieId, isFavorite))
                    },
                    onDirectToAuth = { navController.navigateToAuth() }
                )
            }
        }
        composable(route = Destination.Main.Profile.route) {
            MainNavigationScaffold(navController = navController) {
                ProfileScreen(
                    onBack = {
                        navController.navigate(Destination.Main.Gallery.buildRoute()) {
                            popUpTo(Destination.Main.route)
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onDirectToAuth = { navController.navigateToAuth() }
                )
            }
        }
    }
}