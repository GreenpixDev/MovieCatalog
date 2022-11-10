package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.screen.main.model.MainMovieModel
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.H1
import ru.greenpix.moviecatalog.ui.util.items

fun LazyListScope.mainGallerySection(
    gallery: LazyPagingItems<MainMovieModel>,
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
        MainMovie(
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