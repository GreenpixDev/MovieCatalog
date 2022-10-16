package ru.greenpix.moviecatalog.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO указать шрифт IBM Plex Sans
val Body = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
)

// TODO указать шрифт IBM Plex Sans
val BodySmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = Body,
    body2 = BodySmall
)