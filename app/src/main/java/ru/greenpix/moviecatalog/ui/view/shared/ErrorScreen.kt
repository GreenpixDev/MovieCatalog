package ru.greenpix.moviecatalog.ui.view.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R

@Composable
fun ErrorScreen(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        StyledErrorText(
            visible = true,
            text = text
        )
        StyledButton(
            text = stringResource(id = R.string.repeat),
            onClick = {}
        )
    }
}