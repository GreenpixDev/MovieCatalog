package ru.greenpix.moviecatalog.ui.shared.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.*

/**
 * Стилизированная под приложение кнопка.
 *
 * @param onClick - вызывается, когда пользователь нажимает кнопку
 * @param enabled - контролирует включенное состояние кнопки. При false эта кнопка не будет нажиматься.
 * @param text - текст кнопки
 */
@Composable
fun StyledButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Accent,
            contentColor = BrightWhite,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Accent
        ),
        shape = Shapes.medium,
        border = if (!enabled) BorderStroke(1.dp, GraySilver) else null
    ) {
        Text(
            text = text,
            style = Body
        )
    }
}

@Preview
@Composable
private fun StyledButtonPreview() {
    MovieCatalogTheme {
        StyledButton(
            onClick = {},
            enabled = true,
            text = "Example"
        )
    }
}