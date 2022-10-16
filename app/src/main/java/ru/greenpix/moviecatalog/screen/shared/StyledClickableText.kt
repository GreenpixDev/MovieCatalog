package ru.greenpix.moviecatalog.screen.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.Body
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme
import ru.greenpix.moviecatalog.util.noRippleClickable

@Composable
fun StyledClickableText(
    onClick: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        Text(
            text = text,
            color = Accent,
            style = Body,
            modifier = Modifier
                .align(Alignment.Center)
                .noRippleClickable(onClick),
        )
    }
}

@Preview
@Composable
private fun StyledClickableTextPreview() {
    MovieCatalogTheme {
        StyledClickableText(
            onClick = {},
            text = "Example"
        )
    }
}