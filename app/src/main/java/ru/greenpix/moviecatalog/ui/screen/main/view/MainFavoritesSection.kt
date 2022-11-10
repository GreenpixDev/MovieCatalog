package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.screen.main.model.MainFavoriteModel
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.H1
import ru.greenpix.moviecatalog.ui.util.rememberLazyListFirstPosition

fun LazyListScope.mainFavoritesSection(
    favorites: List<MainFavoriteModel>,
    deletedFavorites: List<MainFavoriteModel>,
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
                            MainFavorite(
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