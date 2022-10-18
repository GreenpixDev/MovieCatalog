package ru.greenpix.moviecatalog.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.greenpix.moviecatalog.R

val IBMPlexSans = FontFamily(
    Font(R.font.imb_plex_sans_regular, FontWeight.Normal),
    Font(R.font.imb_plex_sans_medium, FontWeight.Medium),
    Font(R.font.imb_plex_sans_bold, FontWeight.Bold)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_medium, FontWeight.Medium)
)

val Body = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
)

val BodySmall = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
)

val H1 = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
)

val H2 = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
)

val Title = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Bold,
    fontSize = 36.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp,
)

val Footnote = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
)

val BodyMontserrat = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)

val BodyVerySmall = TextStyle(
    fontFamily = IBMPlexSans,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.sp,
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = Body,
    body2 = BodySmall,
    h1 = H1,
    h2 = H2
)