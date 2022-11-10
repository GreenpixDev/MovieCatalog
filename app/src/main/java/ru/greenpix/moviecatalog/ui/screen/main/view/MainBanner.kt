package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.shared.view.StyledButton
import ru.greenpix.moviecatalog.ui.theme.SealBrown

@Composable
fun MainBanner(
    imageUrl: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        // Постер
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to SealBrown,
                        .25f to Color.Transparent,
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Смотреть
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .padding(bottom = 32.dp)
            ) {
                StyledButton(
                    onClick = onClick,
                    text = stringResource(R.string.watch)
                )
            }
        }
    }
}