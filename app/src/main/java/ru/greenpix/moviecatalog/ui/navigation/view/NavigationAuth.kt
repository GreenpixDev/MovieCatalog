package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.screen.signin.SignInScreen
import ru.greenpix.moviecatalog.ui.screen.signup.SignUpScreen

fun NavGraphBuilder.navigationAuth(navController: NavController) {
    navigation(
        route = Destination.Auth.route,
        startDestination = Destination.Auth.SignIn.route
    ) {
        composable(route = Destination.Auth.SignIn.route) {
            SignInScreen(
                onSuccessSignIn = {
                    navController.navigate(Destination.Main.buildRoute()) {
                        popUpTo(Destination.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onDirectToSignUp = {
                    navController.navigate(Destination.Auth.SignUp.buildRoute()) {
                        popUpTo(Destination.Auth.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(route = Destination.Auth.SignUp.route) {
            SignUpScreen(
                onSuccessSignUp = {
                    navController.navigate(Destination.Main.buildRoute()) {
                        popUpTo(Destination.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onDirectToSignIn = {
                    navController.navigate(Destination.Auth.SignIn.buildRoute()) {
                        popUpTo(Destination.Auth.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}