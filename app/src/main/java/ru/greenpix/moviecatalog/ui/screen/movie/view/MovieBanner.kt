package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.ui.theme.BrightWhite
import ru.greenpix.moviecatalog.ui.theme.Title
import ru.greenpix.moviecatalog.ui.util.roundedAtBottom

// TODO это КАРТОЧКА фильма (Card), а не Box! По семантике это важно! Это указано в ТЗ
@Composable
fun MovieBanner(
    name: String,
    movieImageUrl: String,
    scrollProgress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp).roundedAtBottom())
    ) {
        // Постер
        AsyncImage(
            model = movieImageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Black,
                        1f to Color(0f, 0f, 0f, (scrollProgress * 1.5f).coerceAtMost(1f)),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Название фильма
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                style = Title,
                color = BrightWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}