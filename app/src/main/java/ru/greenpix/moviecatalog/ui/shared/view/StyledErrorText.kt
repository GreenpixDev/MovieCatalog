package ru.greenpix.moviecatalog.ui.shared.view

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.BodySmall
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun StyledErrorText(
    visible: Boolean,
    text: String
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            expandFrom = Alignment.Bottom
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 32.dp)
        ) {
            Text(
                text = text,
                color = Accent,
                style = BodySmall,
                modifier = Modifier
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun StyledErrorTextPreview() {
    MovieCatalogTheme {
        StyledErrorText(
            visible = true,
            text = "Ошибка"
        )
    }
}