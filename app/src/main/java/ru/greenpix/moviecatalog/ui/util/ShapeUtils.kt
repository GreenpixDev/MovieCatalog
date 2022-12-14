package ru.greenpix.moviecatalog.ui.util

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.unit.dp

/**
 * Функция загругления фигуры в начале (слева)
 */
fun CornerBasedShape.roundedAtStart(): CornerBasedShape {
    return copy(
        topStart = topStart,
        topEnd = CornerSize(0.dp),
        bottomStart = bottomStart,
        bottomEnd = CornerSize(0.dp)
    )
}

/**
 * Функция загругления фигуры в конце (справа)
 */
fun CornerBasedShape.roundedAtEnd(): CornerBasedShape {
    return copy(
        topStart = CornerSize(0.dp),
        topEnd = topEnd,
        bottomStart = CornerSize(0.dp),
        bottomEnd = bottomEnd
    )
}

/**
 * Функция загругления фигуры сверху
 */
fun CornerBasedShape.roundedAtTop(): CornerBasedShape {
    return copy(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )
}

/**
 * Функция загругления фигуры снизу
 */
fun CornerBasedShape.roundedAtBottom(): CornerBasedShape {
    return copy(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )
}