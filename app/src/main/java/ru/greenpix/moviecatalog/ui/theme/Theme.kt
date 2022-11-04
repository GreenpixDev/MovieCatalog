package ru.greenpix.moviecatalog.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun MovieCatalogTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = darkColors(
            primary = Accent,
            primaryVariant = AccentFaded,
            secondary = GraySilver,
            background = SealBrown
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}