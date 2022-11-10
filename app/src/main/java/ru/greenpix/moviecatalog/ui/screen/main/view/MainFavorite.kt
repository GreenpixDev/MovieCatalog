package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.Shapes

@Composable
fun MainFavorite(
    imageUrl: String,
    selected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
        .padding(start = 16.dp)
) {
    val width by animateDpAsState(if (selected) 120.dp else 100.dp)
    val height by animateDpAsState(if (selected) 172.dp else 144.dp)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(width, height)
        ) {
            // Постер
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(Shapes.small)
                    .clickable(onClick = onClick)
            )
            // Крестик
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .clickable(onClick = onDelete)
                    .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier.padding(
                            // Такие падинги для того, чтобы пользователю легко было кликать по крестику
                            top = 4.dp,
                            bottom = 16.dp,
                            end = 4.dp,
                            start = 16.dp
                        )
                    )
                }
            }
        }
    }
}