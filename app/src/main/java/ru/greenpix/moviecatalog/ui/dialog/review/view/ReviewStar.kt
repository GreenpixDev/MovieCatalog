package ru.greenpix.moviecatalog.ui.dialog.review.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.GrayFaded

@Composable
fun ReviewStar(
    filled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = (if (filled) Modifier.background(Accent.copy(alpha = .1f), CircleShape) else Modifier)
            .size(24.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = if (filled) {
                painterResource(id = R.drawable.ic_filled_star)
            } else {
                painterResource(id = R.drawable.ic_empty_star)
            },
            colorFilter = ColorFilter.tint(if (filled) Accent else GrayFaded),
            contentDescription = null
        )
    }
}