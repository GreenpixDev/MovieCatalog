package ru.greenpix.moviecatalog.ui.screen.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.ui.screen.main.model.MainFavoriteModel
import ru.greenpix.moviecatalog.ui.screen.main.model.MainMovieModel
import ru.greenpix.moviecatalog.ui.screen.main.model.MainViewState
import ru.greenpix.moviecatalog.ui.screen.main.view.EmptyContentScreen
import ru.greenpix.moviecatalog.ui.screen.main.view.MainBanner
import ru.greenpix.moviecatalog.ui.screen.main.view.mainFavoritesSection
import ru.greenpix.moviecatalog.ui.screen.main.view.mainGallerySection
import ru.greenpix.moviecatalog.ui.shared.view.ErrorScreen
import ru.greenpix.moviecatalog.ui.shared.view.LoadingScreen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.ui.util.firstOrNull
import ru.greenpix.moviecatalog.ui.util.isEmpty
import ru.greenpix.moviecatalog.ui.util.isFirstLoading

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
    favorites: List<MainFavoriteModel>,
    deletedFavorites: List<MainFavoriteModel>,
    gallery: LazyPagingItems<MainMovieModel>,
    onGoToMovie: (String) -> Unit,
    onDeleteFavorite: (String) -> Unit
) {
    LazyColumn {
        item {
            MainBanner(
                imageUrl = gallery.firstOrNull()?.imageUrl ?: "",
                onClick = { gallery.firstOrNull()?.let { onGoToMovie.invoke(it.id) } }
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        mainFavoritesSection(
            favorites = favorites,
            deletedFavorites = deletedFavorites,
            onDeleteFavorite = onDeleteFavorite,
            onGoToMovie = onGoToMovie
        )
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
        mainGallerySection(
            gallery = gallery,
            onGoToMovie = onGoToMovie
        )
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
                    MainFavoriteModel(
                        movieId = "",
                        imageUrl = ""
                    )
                ),
                deletedFavorites = emptyList(),
                gallery = flow<PagingData<MainMovieModel>> {}.collectAsLazyPagingItems(),
                onGoToMovie = {},
                onDeleteFavorite = {}
            )
        }
    }
}