package ru.greenpix.moviecatalog.ui.shared.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.R

/**
 * Аватар пользователя.
 *
 * @param url - ссылка на изображение аватара
 * @param modifier - [Modifier] для этого аватара
 */
@Composable
fun Avatar(
    url: String,
    modifier: Modifier = Modifier
) {
    if (url.isBlank()) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            modifier = modifier.clip(CircleShape)
        )
    }
    else {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(CircleShape)
        )
    }
}