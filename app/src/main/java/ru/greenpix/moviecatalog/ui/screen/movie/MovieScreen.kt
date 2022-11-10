package ru.greenpix.moviecatalog.ui.screen.movie

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieReviewModel
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieViewState
import ru.greenpix.moviecatalog.ui.screen.movie.view.*
import ru.greenpix.moviecatalog.ui.shared.view.ErrorScreen
import ru.greenpix.moviecatalog.ui.shared.view.LoadingScreen
import ru.greenpix.moviecatalog.ui.theme.BodySmall
import ru.greenpix.moviecatalog.ui.theme.BrightWhite
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.ui.util.firstItemScrollProgress
import ru.greenpix.moviecatalog.ui.util.observeAsState

@Composable
fun MovieScreen(
    onBack: () -> Unit,
    onAddReview: (movieId: String) -> Unit,
    onEditReview: (movieId: String, reviewId: String, comment: String, rating: Int, isAnonymous: Boolean) -> Unit,
    onDirectToAuth: () -> Unit,
    movieId: String,
    isFavorite: Boolean,
    viewModel: MovieViewModel = getViewModel()
) {
    // Нужно для обновления экрана, когда закрывается диалог с добавлением review
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()

    var reconnectCount by remember { mutableStateOf(0) }
    val viewState = remember { viewModel.viewState }.value

    when (viewState) {
        is MovieViewState.Loading -> LoadingScreen()
        is MovieViewState.Error -> ErrorScreen(
            text = stringResource(id = viewState.id),
            onRetry = { reconnectCount++ }
        )
        else -> {
            val favorite by remember { viewModel.favoriteState }
            val name by remember { viewModel.nameState }
            val movieImageUrl by remember { viewModel.movieImageUrlState }
            val description by remember { viewModel.descriptionState }
            val year by remember { viewModel.yearState }
            val country by remember { viewModel.countryState }
            val duration by remember { viewModel.durationState }
            val tagline by remember { viewModel.taglineState }
            val producer by remember { viewModel.producerState }
            val budget by remember { viewModel.budgetState }
            val fees by remember { viewModel.feesState }
            val age by remember { viewModel.ageState }
            val genres = remember { viewModel.genresState }
            val myReview by remember { viewModel.myReviewState }
            val myReviewDeleted by remember { viewModel.myReviewDeletedState }
            val otherReviews = remember { viewModel.otherReviewsState }

            MovieContent(
                name = name,
                favorite = favorite,
                movieImageUrl = movieImageUrl,
                description = description,
                year = year,
                country = country,
                duration = duration,
                tagline = tagline,
                producer = producer,
                budget = budget,
                fees = fees,
                age = age,
                genres = genres,
                myReview = myReview,
                myReviewDeleted = myReviewDeleted,
                otherReviews = otherReviews,
                onBack = onBack,
                onToggleFavorite = viewModel::onToggleFavorite,
                onAddReview = { onAddReview(movieId) },
                onEditReview = {
                    val review = myReview
                    if (review != null && !myReviewDeleted) {
                        onEditReview(
                            movieId,
                            review.id,
                            review.comment,
                            review.rating,
                            review.anonymous
                        )
                    }
                },
                onDeleteReview = viewModel::onDeleteReview
            )
        }
    }

    LaunchedEffect(key1 = reconnectCount, key2 = lifecycleState, block = {
        if (lifecycleState == Lifecycle.Event.ON_RESUME) {
            viewModel.load(movieId, isFavorite)
        }
    })

    LaunchedEffect(key1 = viewState, block = {
        if (viewState is MovieViewState.AuthorizationFailed) {
            onDirectToAuth.invoke()
        }
    })
}

@Composable
private fun MovieContent(
    name: String,
    favorite: Boolean,
    movieImageUrl: String,
    description: String,
    year: Int,
    country: String,
    duration: Int,
    tagline: String,
    producer: String,
    budget: Int,
    fees: Int,
    age: Int,
    genres: List<String>,
    myReview: MovieReviewModel?,
    myReviewDeleted: Boolean,
    otherReviews: List<MovieReviewModel>,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit,
    onAddReview: () -> Unit,
    onEditReview: () -> Unit,
    onDeleteReview: () -> Unit
) {
    val lazyState = rememberLazyListState()
    val scrollProgress = lazyState.firstItemScrollProgress()

    LazyColumn(state = lazyState) {
        item {
            MovieBanner(
                name = name,
                movieImageUrl = movieImageUrl,
                scrollProgress = scrollProgress
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                text = description,
                style = BodySmall,
                color = BrightWhite,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            MovieInfo(
                year = year,
                country = country,
                duration = duration,
                tagline = tagline,
                producer = producer,
                budget = budget,
                fees = fees,
                age = age
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            MovieGenres(genres = genres)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        movieReviewsSection(
            myReview = myReview,
            myReviewDeleted = myReviewDeleted,
            otherReviews = otherReviews,
            onAddReview = onAddReview,
            onEditReview = onEditReview,
            onDeleteReview = onDeleteReview
        )
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    MovieHeader(
        name = name,
        scrollProgress = scrollProgress,
        favorite = favorite,
        onBack = onBack,
        onToggleFavorite = onToggleFavorite
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MovieContent(
                name = "Побег из Шоушенка",
                favorite = false,
                movieImageUrl = "",
                description = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения",
                year = 1994,
                country = "США",
                duration = 142,
                tagline = "«Страх - это кандалы. Надежда - это свобода»",
                producer = "Фрэнк Дарабонт",
                budget = 25_000_000,
                fees = 28_418_687,
                age = 16,
                genres = listOf("драма", "боевик", "фантастика", "мелодрама"),
                myReview = null,
                myReviewDeleted = false,
                otherReviews = emptyList(),
                onBack = {},
                onToggleFavorite = {},
                onAddReview = {},
                onEditReview = {},
                onDeleteReview = {}
            )
        }
    }
}