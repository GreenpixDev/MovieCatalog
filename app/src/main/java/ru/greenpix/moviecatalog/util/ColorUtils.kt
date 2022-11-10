package ru.greenpix.moviecatalog.util

/**
 * Функция вычисления оттенка цвета по [рейтингу][rating].
 *
 * Подробнее про оттенок: [androidx.compose.ui.graphics.Color.hsv]
 */
fun calcHueByRating(rating: Float): Float {
    return rating / 10f * 120
}