package ru.greenpix.moviecatalog.ui.dialog.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.dialog.review.model.ReviewViewState
import ru.greenpix.moviecatalog.ui.dialog.review.view.ReviewAnonymousField
import ru.greenpix.moviecatalog.ui.dialog.review.view.ReviewButtons
import ru.greenpix.moviecatalog.ui.dialog.review.view.ReviewCommentField
import ru.greenpix.moviecatalog.ui.dialog.review.view.ReviewRatingField
import ru.greenpix.moviecatalog.ui.theme.BrightWhite
import ru.greenpix.moviecatalog.ui.theme.GrayNero
import ru.greenpix.moviecatalog.ui.theme.H1
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun ReviewDialog(
    movieId: String,
    reviewId: String? = null,
    initComment: String = "",
    initRating: Int = 0,
    initAnonymous: Boolean = false,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    onDirectToAuth: () -> Unit,
    viewModel: ReviewViewModel = getViewModel()
) {
    val viewState by remember { viewModel.viewState }
    val anonymous by remember { viewModel.anonymousState }
    val comment by remember { viewModel.commentState }
    val rating by remember { viewModel.ratingState }
    val canSave by remember { viewModel.canSaveState }

    ReviewDialogContent(
        viewState = viewState,
        anonymous = anonymous,
        comment = comment,
        rating = rating,
        canSave = canSave,
        isNewReview = reviewId == null,
        onAnonymousChange = viewModel::onAnonymousChange,
        onCommentChange = viewModel::onCommentChange,
        onRatingChange = viewModel::onRatingChange,
        onSave = viewModel::onSave,
        onCancel = onCancel
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.load(
            movieId = movieId,
            reviewId = reviewId,
            comment = initComment,
            rating = initRating,
            anonymous = initAnonymous
        )
    })

    LaunchedEffect(key1 = viewState, block = {
        when (viewState) {
            is ReviewViewState.AuthorizationFailed -> onDirectToAuth.invoke()
            is ReviewViewState.Saved -> onSave.invoke()
            else -> {}
        }
    })
}

@Composable
private fun ReviewDialogContent(
    viewState: ReviewViewState,
    anonymous: Boolean,
    comment: String,
    rating: Int,
    canSave: Boolean,
    isNewReview: Boolean,
    onAnonymousChange: (Boolean) -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = GrayNero,
        contentColor = BrightWhite
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.post_review),
                style = H1
            )
            ReviewRatingField(
                value = rating,
                onValueChange = onRatingChange
            )
            ReviewCommentField(
                value = comment,
                onValueChange = onCommentChange
            )
            ReviewAnonymousField(
                value = anonymous,
                enabled = isNewReview,
                onValueChange = onAnonymousChange
            )
            ReviewButtons(
                viewState = viewState,
                canSave = canSave,
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}

@Preview
@Composable
private fun ReviewDialogPreview() {
    MovieCatalogTheme {
        ReviewDialogContent(
            viewState = ReviewViewState.Default,
            anonymous = true,
            comment = "",
            rating = 5,
            canSave = true,
            isNewReview = true,
            onAnonymousChange = {},
            onCommentChange = {},
            onRatingChange = {},
            onSave = {},
            onCancel = {}
        )
    }
}