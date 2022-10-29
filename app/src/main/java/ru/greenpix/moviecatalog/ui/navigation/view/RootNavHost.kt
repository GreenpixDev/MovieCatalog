package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.view.dialog.review.ReviewDialog
import ru.greenpix.moviecatalog.ui.view.screen.main.MainScreen
import ru.greenpix.moviecatalog.ui.view.screen.movie.MovieScreen
import ru.greenpix.moviecatalog.ui.view.screen.profile.ProfileScreen
import ru.greenpix.moviecatalog.ui.view.screen.signin.SignInScreen
import ru.greenpix.moviecatalog.ui.view.screen.signup.SignUpScreen

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

private fun NavGraphBuilder.navigationAuth(navController: NavController) {
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

private fun NavGraphBuilder.navigationMain(navController: NavController) {
    navigation(
        route = Destination.Main.route,
        startDestination = Destination.Main.Gallery.route
    ) {
        composable(route = Destination.Main.Gallery.route) {
            MainNavigationScaffold(navController = navController) {
                MainScreen(
                    onDirectToMovie = { movieId, isFavorite ->
                        navController.navigate(Destination.Movie.buildRoute(movieId, isFavorite))
                    }
                )
            }
        }
        composable(route = Destination.Main.Profile.route) {
            MainNavigationScaffold(navController = navController) {
                ProfileScreen(
                    onBack = { navController.navigateUp() },
                    onDirectToSignIn = {
                        navController.navigate(Destination.Auth.buildRoute()) {
                            popUpTo(Destination.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun NavGraphBuilder.navigationMovie(navController: NavController) {
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
            onBack = { navController.navigateUp() },
            onAddReview = { movieId ->
                navController.navigate(Destination.Review.Add.buildRoute(movieId))
            },
            onEditReview = { movieId, reviewId, comment, rating, isAnonymous ->
                navController.navigate(Destination.Review.Edit.buildRoute(movieId, reviewId, comment, rating, isAnonymous))
            }
        )
    }
}

private fun NavGraphBuilder.navigationReview(navController: NavController) {
    navigation(
        route = Destination.Review.route,
        startDestination = Destination.Review.Add.route
    ) {
        dialog(
            route = Destination.Review.Add.route,
            arguments = listOf(
                navArgument(Destination.Review.Add.MOVIE_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val movieId = it.arguments?.getString(Destination.Review.Add.MOVIE_ID)
            ReviewDialog(
                movieId = checkNotNull(movieId) { "Required value 'movieId' was null." },
                onDismissRequest = { navController.navigateUp() }
            )
        }
        dialog(
            route = Destination.Review.Edit.route,
            arguments = listOf(
                navArgument(Destination.Review.Edit.MOVIE_ID) {
                    type = NavType.StringType
                },
                navArgument(Destination.Review.Edit.REVIEW_ID) {
                    type = NavType.StringType
                },
                navArgument(Destination.Review.Edit.COMMENT) {
                    type = NavType.StringType
                },
                navArgument(Destination.Review.Edit.RATING) {
                    type = NavType.IntType
                },
                navArgument(Destination.Review.Edit.IS_ANONYMOUS) {
                    type = NavType.BoolType
                }
            )
        ) {
            val movieId = it.arguments?.getString(Destination.Review.Edit.MOVIE_ID)
            val reviewId = it.arguments?.getString(Destination.Review.Edit.REVIEW_ID)
            val comment = it.arguments?.getString(Destination.Review.Edit.COMMENT)
            val rating = it.arguments?.getInt(Destination.Review.Edit.RATING)
            val isAnonymous = it.arguments?.getBoolean(Destination.Review.Edit.IS_ANONYMOUS)
            ReviewDialog(
                movieId = checkNotNull(movieId) { "Required value 'movieId' was null." },
                reviewId = checkNotNull(reviewId) { "Required value 'reviewId' was null." },
                initComment = checkNotNull(comment) { "Required value 'comment' was null." },
                initRating = checkNotNull(rating) { "Required value 'rating' was null." },
                initAnonymous = checkNotNull(isAnonymous) { "Required value 'isAnonymous' was null." },
                onDismissRequest = { navController.navigateUp() }
            )
        }
    }
}