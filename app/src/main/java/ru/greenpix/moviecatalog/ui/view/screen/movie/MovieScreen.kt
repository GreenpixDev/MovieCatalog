package ru.greenpix.moviecatalog.ui.view.screen.movie

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.firstItemScrollProgress
import ru.greenpix.moviecatalog.ui.util.observeAsState
import ru.greenpix.moviecatalog.ui.util.roundedAtBottom
import ru.greenpix.moviecatalog.ui.view.screen.movie.model.MovieReview
import ru.greenpix.moviecatalog.ui.view.screen.movie.model.MovieViewState
import ru.greenpix.moviecatalog.ui.view.shared.Avatar
import ru.greenpix.moviecatalog.ui.view.shared.ErrorScreen
import ru.greenpix.moviecatalog.ui.view.shared.LoadingScreen
import ru.greenpix.moviecatalog.util.formatGrouped

private const val WEIGHT_COLUMN_KEY = .3125f
private const val WEIGHT_COLUMN_VALUE = 1 - WEIGHT_COLUMN_KEY

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
    myReview: MovieReview?,
    myReviewDeleted: Boolean,
    otherReviews: List<MovieReview>,
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
            BannerView(
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
            AboutMovieView(
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
            GenresView(genres = genres)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
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
                    ReviewView(
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
            ReviewView(
                isAnonymous = it.anonymous,
                author = it.author,
                avatarUrl = it.avatarUrl,
                comment = it.comment,
                date = it.date,
                rating = it.rating,
                hue = it.hue
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    HeaderView(
        name = name,
        scrollProgress = scrollProgress,
        favorite = favorite,
        onBack = onBack,
        onToggleFavorite = onToggleFavorite
    )
}

// TODO это КАРТОЧКА фильма (Card), а не Box! По семантике это важно! Это указано в ТЗ
@Composable
private fun BannerView(
    name: String,
    movieImageUrl: String,
    scrollProgress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp).roundedAtBottom())
    ) {
        // Постер
        AsyncImage(
            model = movieImageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
               .fillMaxSize()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Black,
                        1f to Color(0f, 0f, 0f, (scrollProgress * 1.5f).coerceAtMost(1f)),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Название фильма
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                style = Title,
                color = BrightWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun HeaderView(
    name: String,
    scrollProgress: Float,
    favorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val density = LocalDensity.current
    val visible = scrollProgress > .5f
    val heartColor by animateColorAsState(targetValue = if (visible) Accent else BaseWhite)

    // Код AnimatedVisibility взят с https://developer.android.com/jetpack/compose/animation
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .background(SealBrown)
                .statusBarsPadding(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 32.dp)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = name,
                    style = H1,
                    color = BrightWhite,
                    modifier = Modifier
                        .padding(horizontal = 52.dp),
                )
            }
        }
    }
    Box(
        modifier = Modifier.statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onToggleFavorite)
            ) {
                Image(
                    painter = if (favorite) {
                        painterResource(R.drawable.ic_filled_heart)
                    } else {
                        painterResource(R.drawable.ic_empty_heart)
                    },
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(heartColor),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun AboutMovieView(
    year: Int,
    country: String,
    duration: Int,
    tagline: String,
    producer: String,
    budget: Int,
    fees: Int,
    age: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Subtitle(text = stringResource(R.string.about_movie))
        TableRowView(key = stringResource(R.string.year), value = year.toString())
        if (country.isNotBlank()) {
            TableRowView(key = stringResource(R.string.country), value = country)
        }
        TableRowView(key = stringResource(R.string.duration), value = "$duration мин.")
        if (tagline.isNotBlank()) {
            TableRowView(key = stringResource(R.string.tagline), value = "«$tagline»")
        }
        if (producer.isNotBlank()) {
            TableRowView(key = stringResource(R.string.producer), value = producer)
        }
        if (budget >= 0) {
            TableRowView(key = stringResource(R.string.budget), value = "\$${budget.formatGrouped()}")
        }
        if (fees >= 0) {
            TableRowView(key = stringResource(R.string.fees_in_the_world), value = "\$${fees.formatGrouped()}")
        }
        TableRowView(key = stringResource(R.string.age), value = "$age+")
    }
}

@Composable
private fun GenresView(
    genres: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Subtitle(text = stringResource(R.string.genres))
        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp,
        ) {
            genres.forEach { GenreView(it) }
        }
    }
}

@Composable
private fun GenreView(
    name: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Accent, Shapes.small)
            .clip(Shapes.small)
            .height(27.dp)
    ) {
        Text(
            text = name,
            style = BodyMontserrat,
            color = BaseWhite,
            modifier = Modifier.padding(horizontal = 16.dp)
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
        Subtitle(text = stringResource(R.string.reviews))
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

@Composable
private fun ReviewView(
    isAnonymous: Boolean,
    author: String,
    avatarUrl: String,
    comment: String,
    date: String,
    rating: Int,
    hue: Float,
    isMine: Boolean = false,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .border(1.dp, GrayFaded, Shapes.small)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Аватар, имя пользователя и оценка
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Avatar(
                    url = if (isAnonymous) "" else avatarUrl,
                    modifier = Modifier.size(40.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isAnonymous) stringResource(id = R.string.anonymous_review) else author,
                        style = Body,
                        color = BrightWhite
                    )
                    if (isMine) {
                        Text(
                            text = stringResource(R.string.my_review),
                            style = BodyVerySmall,
                            color = GraySilver
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(42.dp, 28.dp)
                        .background(
                            color = Color.hsv(hue, .99f, .67f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating.toString(),
                        style = Body,
                        color = BrightWhite
                    )
                }
            }
            // Описание
            Text(
                text = comment,
                style = BodySmall,
                color = BrightWhite
            )
            // Дата и кнопочки редактирования и удаления
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = date,
                    style = BodyVerySmall,
                    color = GraySilver
                )
                if (isMine) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onEdit)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onDelete)
                    )
                }
            }
        }
    }
}

@Composable
private fun TableRowView(
    key: String,
    value: String
) {
    Row {
        Text(
            text = key,
            style = BodyMontserrat,
            color = GraySilver,
            modifier = Modifier.weight(WEIGHT_COLUMN_KEY)
        )
        Text(
            text = value,
            style = BodyMontserrat,
            color = BrightWhite,
            modifier = Modifier.weight(WEIGHT_COLUMN_VALUE)
        )
    }
}

@Composable
private fun Subtitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Body,
        color = BrightWhite,
        modifier = modifier.padding(bottom = 8.dp)
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

@Preview(showBackground = true)
@Composable
private fun ReviewViewPreview() {
    MovieCatalogTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            ReviewView(
                isAnonymous = false,
                author = "Роман",
                avatarUrl = "",
                comment = "Сразу скажу, что фильм мне понравился. Люблю Фримэна, уважаю Роббинса. Читаю Кинга. Но рецензия красненькая.",
                date = "07.10.2022",
                rating = 1,
                hue = 0f,
                isMine = true
            )
        }
    }
}