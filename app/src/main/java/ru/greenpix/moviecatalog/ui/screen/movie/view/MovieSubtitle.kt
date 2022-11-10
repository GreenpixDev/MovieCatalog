package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.Body
import ru.greenpix.moviecatalog.ui.theme.BrightWhite

@Composable
fun MovieSubtitle(
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