package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieReviewModel
import ru.greenpix.moviecatalog.ui.theme.Accent

fun LazyListScope.movieReviewsSection(
    myReview: MovieReviewModel?,
    myReviewDeleted: Boolean,
    otherReviews: List<MovieReviewModel>,
    onAddReview: () -> Unit,
    onEditReview: () -> Unit,
    onDeleteReview: () -> Unit
) {
    item {
        ReviewsSubtitleView(
            hiddenAddReview = myReview != null && !myReviewDeleted,
            onAddReview = onAddReview
        )
    }
    item {
        AnimatedVisibility(
            visible = myReview != null && !myReviewDeleted
        ) {
            if (myReview != null) {
                MovieReview(
                    isAnonymous = myReview.anonymous,
                    author = myReview.author,
                    avatarUrl = myReview.avatarUrl,
                    comment = myReview.comment,
                    date = myReview.date,
                    rating = myReview.rating,
                    hue = myReview.hue,
                    isMine = true,
                    onEdit = onEditReview,
                    onDelete = onDeleteReview
                )
            }
        }
    }
    items(otherReviews) {
        MovieReview(
            isAnonymous = it.anonymous,
            author = it.author,
            avatarUrl = it.avatarUrl,
            comment = it.comment,
            date = it.date,
            rating = it.rating,
            hue = it.hue
        )
    }
}

@Composable
private fun ReviewsSubtitleView(
    hiddenAddReview: Boolean,
    onAddReview: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        MovieSubtitle(text = stringResource(R.string.reviews))
        Spacer(modifier = Modifier.weight(1f))
        if (!hiddenAddReview) {
            Image(
                painter = painterResource(R.drawable.ic_plus),
                colorFilter = ColorFilter.tint(Accent),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onAddReview)
            )
        }
    }
}