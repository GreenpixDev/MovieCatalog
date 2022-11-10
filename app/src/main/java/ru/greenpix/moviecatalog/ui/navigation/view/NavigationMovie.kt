package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.screen.movie.MovieScreen

fun NavGraphBuilder.navigationMovie(navController: NavController) {
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
            onAddReview = { movieReviewId ->
                navController.navigate(Destination.Review.Add.buildRoute(movieReviewId))
            },
            onEditReview = { movieReviewId, reviewId, comment, rating, isAnonymous ->
                navController.navigate(Destination.Review.Edit.buildRoute(movieReviewId, reviewId, comment, rating, isAnonymous))
            },
            onDirectToAuth = { navController.navigateToAuth() }
        )
    }
}
