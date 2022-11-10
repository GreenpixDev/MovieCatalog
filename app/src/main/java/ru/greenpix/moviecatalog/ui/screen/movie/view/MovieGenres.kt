package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.BaseWhite
import ru.greenpix.moviecatalog.ui.theme.BodyMontserrat
import ru.greenpix.moviecatalog.ui.theme.Shapes

@Composable
fun MovieGenres(
    genres: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        MovieSubtitle(text = stringResource(R.string.genres))
        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp,
        ) {
            genres.forEach { Genre(it) }
        }
    }
}

@Composable
private fun Genre(
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