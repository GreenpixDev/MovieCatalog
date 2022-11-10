package ru.greenpix.moviecatalog.ui.dialog.review.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.dialog.review.model.ReviewViewState
import ru.greenpix.moviecatalog.ui.shared.view.StyledButton
import ru.greenpix.moviecatalog.ui.shared.view.StyledClickableText
import ru.greenpix.moviecatalog.ui.shared.view.StyledErrorText

@Composable
fun ReviewButtons(
    viewState: ReviewViewState,
    canSave: Boolean,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StyledErrorText(
            visible = viewState is ReviewViewState.Error,
            text = if (viewState is ReviewViewState.Error) {
                stringResource(id = viewState.id)
            } else {
                ""
            }
        )
        StyledButton(
            onClick = onSave,
            enabled = canSave,
            text = stringResource(id = R.string.save)
        )
        StyledClickableText(
            onClick = onCancel,
            text = stringResource(R.string.cancel)
        )
    }
}