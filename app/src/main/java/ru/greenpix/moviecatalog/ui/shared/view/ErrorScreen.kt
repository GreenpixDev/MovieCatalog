package ru.greenpix.moviecatalog.ui.shared.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R

/**
 * Экран ошибки.
 *
 * @param text текст ошибки
 * @param onRetry - обратный вызов, который запускается, когда пользователь нажимает на кнопку "Повторить попытку".
 */
@Composable
fun ErrorScreen(
    text: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyledErrorText(
            visible = true,
            text = text
        )
        Spacer(modifier = Modifier.padding(8.dp))
        StyledButton(
            text = stringResource(id = R.string.retry),
            onClick = onRetry
        )
    }
}