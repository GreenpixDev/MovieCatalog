package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.view.screen.main.MainScreen
import ru.greenpix.moviecatalog.ui.view.screen.movie.MovieScreen
import ru.greenpix.moviecatalog.ui.view.screen.profile.ProfileScreen
import ru.greenpix.moviecatalog.ui.view.screen.signin.SignInScreen
import ru.greenpix.moviecatalog.ui.view.screen.signup.SignUpScreen
import ru.greenpix.moviecatalog.ui.view.shared.MainNavigationBar

@Composable
fun RootNavHost(
    navController: NavHostController,
    startDestination: String
) {
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isMainNavBarActive = currentDestination?.hierarchy?.any {
                it.route == Destination.Main.route || it.route == Destination.Profile.route
            } == true

            if (isMainNavBarActive) {
                MainNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(route = Destination.SignIn.route) {
                    SignInScreen(
                        onSuccessSignIn = {
                            navController.navigate(Destination.Main.route) // TODO popUp
                        },
                        onDirectToSignUp = {
                            navController.navigate(Destination.SignUp.route)
                        }
                    )
                }
                composable(route = Destination.SignUp.route) {
                    SignUpScreen(
                        onSuccessSignUp = {
                            navController.navigate(Destination.Main.route) // TODO popUp
                        },
                        onDirectToSignIn = {
                            navController.navigate(Destination.SignIn.route)
                        }
                    )
                }
                composable(route = Destination.Main.route) {
                    MainScreen(
                        onDirectToMovie = { movieId ->
                            navController.navigate(Destination.Movie.buildRoute(movieId))
                        }
                    )
                }
                composable(route = Destination.Profile.route) {
                    ProfileScreen(
                        onBack = { navController.navigateUp() },
                        onDirectToSignIn = { navController.navigate(Destination.SignIn.route) } // TODO popUp
                    )
                }
                composable(
                    route = Destination.Movie.route,
                    arguments = listOf(
                        navArgument(Destination.Movie.MOVIE_ID) {
                            type = NavType.StringType
                        },
                        navArgument(Destination.Movie.IS_FAVORITE) {
                            type = NavType.BoolType
                        }
                    )
                ) {
                    val movieId = it.arguments?.getString(Destination.Movie.MOVIE_ID)
                    val isFavorite = it.arguments?.getBoolean(Destination.Movie.IS_FAVORITE)
                    MovieScreen(
                        movieId = checkNotNull(movieId) { "Required value 'movieId' was null." },
                        isFavorite = checkNotNull(isFavorite) { "Required value 'isFavorite' was null." },
                        onBack = { navController.navigateUp() }
                    )
                }
                dialog(route = Destination.Review.route) {
                    // TODO
                }
            }
        }
    }
}