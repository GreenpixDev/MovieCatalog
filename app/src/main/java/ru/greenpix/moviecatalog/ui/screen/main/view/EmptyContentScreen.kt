package ru.greenpix.moviecatalog.ui.screen.main.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.shared.view.StyledErrorText

@Composable
fun EmptyContentScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StyledErrorText(
            visible = true,
            text = stringResource(id = R.string.empty_content)
        )
    }
}