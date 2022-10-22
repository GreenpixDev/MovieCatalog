package ru.greenpix.moviecatalog.ui.view.screen.home.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.navigation.Router
import ru.greenpix.moviecatalog.ui.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.items
import ru.greenpix.moviecatalog.ui.util.itemsIndexed
import ru.greenpix.moviecatalog.ui.util.rememberLazyListFirstPosition
import ru.greenpix.moviecatalog.ui.view.screen.home.main.model.MainFavorite
import ru.greenpix.moviecatalog.ui.view.screen.home.main.model.MainMovie
import ru.greenpix.moviecatalog.ui.view.shared.LoadingScreen
import ru.greenpix.moviecatalog.ui.view.shared.StyledButton
import ru.greenpix.moviecatalog.ui.view.shared.model.LoadState
import ru.greenpix.moviecatalog.util.format

@Composable
fun MainScreen(
    router: Router = Router(),
    viewModel: MainViewModel = getViewModel()
) {
    val loadState by remember { viewModel.loadState }

    if (loadState != LoadState.LOADED) {
        LoadingScreen()
    }
    else {
        val favorites = remember { viewModel.favoritesState }
        val gallery = remember { viewModel.galleryState }

        if (gallery.isEmpty()) {
            // TODO отсутствие контента
        }
        else {
            MainContent(
                favorites = favorites,
                gallery = gallery,
                onGoToMovie = { router.routeTo(Screen.Movie) }, // TODO изменить навигацию
                onDeleteFavorite = viewModel::onDeleteFavorite
            )
        }
    }

    LaunchedEffect(key1 = loadState, block = {
        viewModel.load()
    })
}

@Composable
private fun MainContent(
    favorites: Map<Int, MainFavorite>,
    gallery: Map<Int, MainMovie>,
    onGoToMovie: (Int) -> Unit,
    onDeleteFavorite: (Int) -> Unit
) {
    LazyColumn {
        item {
            BannerView(
                // TODO а если список пустой? Будет ошибка. Нужно отработать эту ситуацию.
                imageUrl = gallery.firstNotNullOf { it.value.imageUrl },
                onClick = { onDeleteFavorite.invoke(gallery.firstNotNullOf { it.key } ) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        if (favorites.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.favorites),
                    style = H1,
                    color = Accent,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                )
            }
            item {
                // Поиск центрального элемента
                // https://stackoverflow.com/questions/71832396/jetpack-compose-lazylist-possible-to-zoom-the-center-item
                val lazyState = rememberLazyListState()
                val centerPosition by rememberLazyListFirstPosition(state = lazyState)

                LazyRow(
                    modifier = Modifier
                        .height(172.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    state = lazyState
                ) {
                    itemsIndexed(favorites) { index, id, item ->
                        FavoriteView(
                            imageUrl = item.imageUrl,
                            selected = index == centerPosition,
                            onClick = { onGoToMovie.invoke(id) },
                            onDelete = { onDeleteFavorite.invoke(id) }
                        )
                    }
                    item { Spacer(modifier = Modifier.width(16.dp)) }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            Text(
                text = stringResource(R.string.gallery),
                style = H1,
                color = Accent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
        items(gallery) { id, item ->
            MovieView(
                name = item.name,
                imageUrl = item.imageUrl,
                year = item.year,
                country = item.country,
                genres = item.genres,
                rating = item.rating,
                hue = item.hue,
                onClick = { onGoToMovie.invoke(id) }
            )
        }
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

@Composable
private fun FavoriteView(
    imageUrl: String,
    selected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val width by animateDpAsState(if (selected) 120.dp else 100.dp)
    val height by animateDpAsState(if (selected) 172.dp else 144.dp)

    Box(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
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
                Image(
                    painter = painterResource(R.drawable.ic_cross),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable(onClick = onDelete)
                )
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
                .height(144.dp)
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
            Column {
                // Название
                Text(
                    text = name,
                    style = H2,
                    color = BrightWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Год и страна производства
                Text(
                    text = "$year • $country",
                    style = BodySmall,
                    color = BrightWhite,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Жанры
                Text(
                    text = genres,
                    style = BodySmall,
                    color = BrightWhite,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                // Средняя оценка
                Box(
                    modifier = Modifier
                        .size(56.dp, 28.dp)
                        .background(
                            color = Color.hsv(hue, .99f, .67f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating.format(1),
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
                favorites = mapOf(
                    1 to MainFavorite(imageUrl = "")
                ),
                gallery = mapOf(
                    1 to MainMovie(
                        name = "Пираты карибского моря. Проклятие черной жемчужины",
                        imageUrl = "",
                        year = 2003,
                        country = "США",
                        genres = "драма, романтика, фантастика, приключение",
                        rating = 5.5f
                    )
                ),
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