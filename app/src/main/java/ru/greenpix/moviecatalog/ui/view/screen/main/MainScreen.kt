package ru.greenpix.moviecatalog.ui.view.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.*
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainFavorite
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainMovie
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainViewState
import ru.greenpix.moviecatalog.ui.view.shared.EmptyContentScreen
import ru.greenpix.moviecatalog.ui.view.shared.ErrorScreen
import ru.greenpix.moviecatalog.ui.view.shared.LoadingScreen
import ru.greenpix.moviecatalog.ui.view.shared.StyledButton
import ru.greenpix.moviecatalog.util.format

@Composable
fun MainScreen(
    onDirectToMovie: (String, Boolean) -> Unit,
    onDirectToAuth: () -> Unit,
    viewModel: MainViewModel = getViewModel()
) {
    var reconnectCount by remember { mutableStateOf(0) }
    val viewState = remember { viewModel.viewState }.value
    val gallery = viewModel.galleryFlow.collectAsLazyPagingItems()

    if (viewState is MainViewState.Loading || gallery.isFirstLoading()) {
        LoadingScreen()
    }
    else when (viewState) {
        is MainViewState.Error -> ErrorScreen(
            text = stringResource(id = viewState.id),
            onRetry = { reconnectCount++ }
        )
        else -> {
            if (gallery.isEmpty()) {
                EmptyContentScreen()
            }
            else {
                val favorites = remember { viewModel.favoritesState }
                val deletedFavorites = remember { viewModel.deletedFavoritesState }

                MainContent(
                    favorites = favorites,
                    deletedFavorites = deletedFavorites,
                    gallery = gallery,
                    onGoToMovie = {
                        onDirectToMovie(it, viewModel.isFavoriteMovie(it))
                    },
                    onDeleteFavorite = viewModel::onDeleteFavorite
                )
            }
        }
    }

    LaunchedEffect(key1 = reconnectCount, block = {
        gallery.retry()
        viewModel.load()
    })

    LaunchedEffect(key1 = viewState, block = {
        if (viewState is MainViewState.AuthorizationFailed) {
            onDirectToAuth.invoke()
        }
    })
}

@Composable
private fun MainContent(
    favorites: List<MainFavorite>,
    deletedFavorites: List<MainFavorite>,
    gallery: LazyPagingItems<MainMovie>,
    onGoToMovie: (String) -> Unit,
    onDeleteFavorite: (String) -> Unit
) {
    LazyColumn {
        item {
            BannerView(
                imageUrl = gallery.firstOrNull()?.imageUrl ?: "",
                onClick = { gallery.firstOrNull()?.let { onGoToMovie.invoke(it.id) } }
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        itemsFavorites(
            favorites = favorites,
            deletedFavorites = deletedFavorites,
            onDeleteFavorite = onDeleteFavorite,
            onGoToMovie = onGoToMovie
        )
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        itemsGallery(
            gallery = gallery,
            onGoToMovie = onGoToMovie
        )
    }
}

@Composable
private fun BannerView(
    imageUrl: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        // Постер
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to SealBrown,
                        .25f to Color.Transparent,
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Смотреть
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .padding(bottom = 32.dp)
            ) {
                StyledButton(
                    onClick = onClick,
                    text = stringResource(R.string.watch)
                )
            }
        }
    }
}

private fun LazyListScope.itemsFavorites(
    favorites: List<MainFavorite>,
    deletedFavorites: List<MainFavorite>,
    onGoToMovie: (String) -> Unit,
    onDeleteFavorite: (String) -> Unit
) {
    item {
        AnimatedVisibility(
            visible = favorites.size - deletedFavorites.size > 0
        ) {
            Column {
                Text(
                    text = stringResource(R.string.favorites),
                    style = H1,
                    color = Accent,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                )
                // Поиск центрального элемента
                // https://stackoverflow.com/questions/71832396/jetpack-compose-lazylist-possible-to-zoom-the-center-item
                val lazyRowState = rememberLazyListState()
                val centerPosition by rememberLazyListFirstPosition(state = lazyRowState)

                LazyRow(
                    modifier = Modifier
                        .height(172.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    state = lazyRowState
                ) {
                    itemsIndexed(favorites) { index, item ->
                        AnimatedVisibility(
                            visible = item !in deletedFavorites,
                            exit = shrinkHorizontally()
                        ) {
                            FavoriteView(
                                imageUrl = item.imageUrl,
                                selected = index == centerPosition,
                                onClick = { onGoToMovie.invoke(item.movieId) },
                                onDelete = { onDeleteFavorite.invoke(item.movieId) }
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                }
            }
        }
    }
}

private fun LazyListScope.itemsGallery(
    gallery: LazyPagingItems<MainMovie>,
    onGoToMovie: (String) -> Unit
) {
    item {
        Text(
            text = stringResource(R.string.gallery),
            style = H1,
            color = Accent,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
    items(gallery) { item ->
        MovieView(
            name = item.name,
            imageUrl = item.imageUrl,
            year = item.year,
            country = item.country,
            genres = item.genres,
            rating = item.rating,
            hue = item.hue,
            onClick = { onGoToMovie.invoke(item.id) }
        )
    }
    if (gallery.loadState.append == LoadState.Loading) {
        item {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
    item {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun FavoriteView(
    imageUrl: String,
    selected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
        .padding(start = 16.dp)
) {
    val width by animateDpAsState(if (selected) 120.dp else 100.dp)
    val height by animateDpAsState(if (selected) 172.dp else 144.dp)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(width, height)
        ) {
            // Постер
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(Shapes.small)
                    .clickable(onClick = onClick)
            )
            // Крестик
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .clickable(onClick = onDelete)
                    .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier.padding(
                            // Такие падинги для того, чтобы пользователю легко было кликать по крестику
                            top = 4.dp,
                            bottom = 16.dp,
                            end = 4.dp,
                            start = 16.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieView(
    name: String,
    imageUrl: String,
    year: Int,
    country: String,
    genres: String,
    rating: Float,
    hue: Float,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {
            // Постер
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp, 144.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.defaultMinSize(minHeight = 144.dp)
            ) {
                // Название
                Text(
                    text = name,
                    style = H2,
                    color = BrightWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Год и страна производства
                Text(
                    text = "$year • $country",
                    style = BodySmall,
                    color = BrightWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Жанры
                Text(
                    text = genres,
                    style = BodySmall,
                    color = BrightWhite
                )
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.weight(1f))
                // Средняя оценка
                Box(
                    modifier = Modifier
                        .size(56.dp, 28.dp)
                        .background(
                            color = if (rating.isNaN()) {
                                Gray
                            } else {
                                Color.hsv(hue, .99f, .67f)
                            },
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (rating.isNaN()) {
                            "—"
                        } else {
                            rating.format(1)
                        },
                        style = Body,
                        color = BrightWhite
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MainContent(
                favorites = listOf(
                    MainFavorite(
                        movieId = "",
                        imageUrl = ""
                    )
                ),
                deletedFavorites = emptyList(),
                gallery = flow<PagingData<MainMovie>> {}.collectAsLazyPagingItems(),
                onGoToMovie = {},
                onDeleteFavorite = {}
            )
        }
    }
}

@Preview
@Composable
private fun MovieViewPreview() {
    MovieCatalogTheme {
        MovieView(
            name = "Пираты карибского моря. Проклятие черной жемчужины",
            imageUrl = "",
            year = 2003,
            country = "США",
            genres = "драма, драма, драма, драма, драма, драма, драма, драма, драма, драма, драма, драма, драма",
            rating = 6.555f,
            hue = 80f,
            onClick = {}
        )
    }
}