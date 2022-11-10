package ru.greenpix.moviecatalog.ui.dialog.review.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun ReviewAnonymousField(
    value: Boolean,
    enabled: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.anonymous_review),
            style = Body,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = (if (enabled) Modifier.clickable { onValueChange.invoke(!value) } else Modifier)
                .size(24.dp)
                .clip(Shapes.medium)
                .border(1.dp, GrayFaded, Shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            if (value) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    colorFilter = ColorFilter.tint(if (enabled) Accent else Gray),
                    contentDescription = null
                )
            }
        }
    }
}