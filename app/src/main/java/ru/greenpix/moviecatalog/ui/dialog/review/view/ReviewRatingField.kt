package ru.greenpix.moviecatalog.ui.dialog.review.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReviewRatingField(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 1..10) {
            ReviewStar(
                filled = i <= value,
                onClick = { onValueChange.invoke(i) }
            )
        }
    }
}