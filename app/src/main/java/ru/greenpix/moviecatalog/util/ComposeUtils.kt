package ru.greenpix.moviecatalog.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun CornerBasedShape.roundedAtStart(): CornerBasedShape {
    return copy(
        topStart = topStart,
        topEnd = CornerSize(0.dp),
        bottomStart = bottomStart,
        bottomEnd = CornerSize(0.dp)
    )
}

fun CornerBasedShape.roundedAtEnd(): CornerBasedShape {
    return copy(
        topStart = CornerSize(0.dp),
        topEnd = topEnd,
        bottomStart = CornerSize(0.dp),
        bottomEnd = bottomEnd
    )
}

fun CornerBasedShape.roundedAtTop(): CornerBasedShape {
    return copy(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )
}

fun CornerBasedShape.roundedAtBottom(): CornerBasedShape {
    return copy(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )
}