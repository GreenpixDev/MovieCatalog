package ru.greenpix.moviecatalog.ui.view.shared

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R

@Composable
fun MovieCatalogLogo(
    visibleState: MutableState<Boolean> = remember { mutableStateOf(false) },
    scaled: Boolean,
    modifier: Modifier = Modifier
) {
    val logoWidth by animateDpAsState(if (visibleState.value == scaled) 250.dp else 147.41.dp)
    val logoHeight by animateDpAsState(if (visibleState.value == scaled) 170.dp else 100.dp)

    Box(
        modifier = modifier.size(250.dp, 170.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.named_logo),
            contentDescription = null,
            modifier = Modifier.size(logoWidth, logoHeight)
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        visibleState.value = true
    })
}