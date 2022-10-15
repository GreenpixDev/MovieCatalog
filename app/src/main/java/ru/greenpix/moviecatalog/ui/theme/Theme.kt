package ru.greenpix.moviecatalog.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import ru.greenpix.moviecatalog.R

@Composable
fun MovieCatalogTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = darkColors(
            primary = Accent,
            primaryVariant = AccentFaded,
            secondary = Gray,
            background = colorResource(id = R.color.background)
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}