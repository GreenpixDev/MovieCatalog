package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun MovieHeader(
    name: String,
    scrollProgress: Float,
    favorite: Boolean,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val density = LocalDensity.current
    val visible = scrollProgress > .5f
    val heartColor by animateColorAsState(targetValue = if (visible) Accent else BaseWhite)

    // Код AnimatedVisibility взят с https://developer.android.com/jetpack/compose/animation
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .background(SealBrown)
                .statusBarsPadding(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 32.dp)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = name,
                    style = H1,
                    color = BrightWhite,
                    modifier = Modifier
                        .padding(horizontal = 52.dp),
                )
            }
        }
    }
    Box(
        modifier = Modifier.statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onBack)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onToggleFavorite)
            ) {
                Image(
                    painter = if (favorite) {
                        painterResource(R.drawable.ic_filled_heart)
                    } else {
                        painterResource(R.drawable.ic_empty_heart)
                    },
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(heartColor),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}