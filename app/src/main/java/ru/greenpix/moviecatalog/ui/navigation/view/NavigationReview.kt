package ru.greenpix.moviecatalog.ui.navigation.view

import androidx.navigation.*
import androidx.navigation.compose.dialog
import ru.greenpix.moviecatalog.ui.dialog.review.ReviewDialog
import ru.greenpix.moviecatalog.ui.navigation.Destination

fun NavGraphBuilder.navigationReview(navController: NavController) {
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
                onCancel = { navController.navigateUp() },
                onSave = { navController.navigateUp() },
                onDirectToAuth = { navController.navigateToAuth() }
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
                onCancel = { navController.navigateUp() },
                onSave = { navController.navigateUp() },
                onDirectToAuth = { navController.navigateToAuth() }
            )
        }
    }
}