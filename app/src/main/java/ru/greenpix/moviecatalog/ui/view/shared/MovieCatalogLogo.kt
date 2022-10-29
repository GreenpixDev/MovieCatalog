package ru.greenpix.moviecatalog.ui.view.shared

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R

@Composable
fun MovieCatalogLogo(
    animate: Boolean = true,
    scaled: Boolean,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    val logoWidth by animateDpAsState(if (visible == scaled) 250.dp else 147.41.dp)
    val logoHeight by animateDpAsState(if (visible == scaled) 170.dp else 100.dp)

    Box(
        modifier = modifier.height(170.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.named_logo),
            contentDescription = null,
            modifier = Modifier.size(logoWidth, logoHeight)
        )
    }

    if (animate) {
        LaunchedEffect(key1 = Unit, block = {
            visible = true
        })
    }
}