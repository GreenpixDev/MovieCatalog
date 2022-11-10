package ru.greenpix.moviecatalog.ui.dialog.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.dialog.review.model.ReviewViewState
import ru.greenpix.moviecatalog.ui.shared.view.StyledButton
import ru.greenpix.moviecatalog.ui.shared.view.StyledClickableText
import ru.greenpix.moviecatalog.ui.shared.view.StyledErrorText
import ru.greenpix.moviecatalog.ui.theme.*

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
            RatingField(
                value = rating,
                onValueChange = onRatingChange
            )
            CommentField(
                value = comment,
                onValueChange = onCommentChange
            )
            AnonymousField(
                value = anonymous,
                enabled = isNewReview,
                onValueChange = onAnonymousChange
            )
            Buttons(
                viewState = viewState,
                canSave = canSave,
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}

@Composable
private fun RatingField(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 1..10) {
            StarImage(
                filled = i <= value,
                onClick = { onValueChange.invoke(i) }
            )
        }
    }
}

@Composable
private fun CommentField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = BrightWhite,
        contentColor = SealBrown,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = BodySmall,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.review),
                    style = BodyMontserrat,
                    color = Gray,
                    fontSize = 13.7.sp
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = SealBrown,
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            )
        )
    }
}

@Composable
private fun AnonymousField(
    value: Boolean,
    enabled: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.anonymous_review),
            style = Body,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = (if (enabled) Modifier.clickable { onValueChange.invoke(!value) } else Modifier)
                .size(24.dp)
                .clip(Shapes.medium)
                .border(1.dp, GrayFaded, Shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            if (value) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    colorFilter = ColorFilter.tint(if (enabled) Accent else Gray),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun Buttons(
    viewState: ReviewViewState,
    canSave: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StyledErrorText(
            visible = viewState is ReviewViewState.Error,
            text = if (viewState is ReviewViewState.Error) {
                stringResource(id = viewState.id)
            } else {
                ""
            }
        )
        StyledButton(
            onClick = onSave,
            enabled = canSave,
            text = stringResource(id = R.string.save)
        )
        StyledClickableText(
            onClick = onCancel,
            text = stringResource(R.string.cancel)
        )
    }
}

@Composable
private fun StarImage(
    filled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = (if (filled) Modifier.background(Accent.copy(alpha = .1f), CircleShape) else Modifier)
            .size(24.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (filled) {
                painterResource(id = R.drawable.ic_filled_star)
            } else {
                painterResource(id = R.drawable.ic_empty_star)
            },
            colorFilter = ColorFilter.tint(if (filled) Accent else GrayFaded),
            contentDescription = null
        )
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