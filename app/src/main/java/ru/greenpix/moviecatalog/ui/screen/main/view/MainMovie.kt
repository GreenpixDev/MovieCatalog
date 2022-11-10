package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.util.format

@Composable
fun MainMovie(
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

@Preview
@Composable
private fun MainMoviePreview() {
    MovieCatalogTheme {
        MainMovie(
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