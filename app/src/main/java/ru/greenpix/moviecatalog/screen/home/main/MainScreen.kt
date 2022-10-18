package ru.greenpix.moviecatalog.screen.home.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.screen.shared.StyledButton
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.util.compose.rememberLazyListFirstPosition
import ru.greenpix.moviecatalog.util.format

private const val exampleImageUrl = "https://kinopoisk-ru.clstorage.net/15D2S2r30/33deda7SK/7htRvK7qLcqB7zW47Pf5ERgu4sxJy-F4M37R8H0rzARd-esRa2vgXRtB0HIEO4I3Exzc257O-lDcQ0AMPsqM098SBI3_lmmv4LpMkcm72FBpLpOhGMOEWWpupb0IRrC4nVkjtKPzHP2b8FVrBTv3QmZ93Ir-__pt7BpNxNaawXp6CxEMfhagTpHbcKgw1ykRpG_2nH2W_Ug7c03ozQeoeEkFPxhLj-TdV2w1jw32S_ZKUbASRvv2kJjWHYQvjoUC1kf58MqGrL75X3w8ALPFtRxbTiBhonHsa-PRTOEbxIDt5YMYE4f4_RvoyfPp6hc6ki0ATpL37lQwksGIa7Ih6qoTscie5tBG_ft0zOxPUbGYS7qohdJJgJMHyUBVT2DY_eUvAAfnbEWf0MkTYXrX4mah9FpelyLctB7dIBPGYfJyb0HYOhog7tUvxDTMl32FuO-GJEGCRVyvDwE4UaMwzDVN5_QHgzRd3wTZ-zFqV2Ki8UAW4m_-DADqyQif0rXyRqMN5MJG3F6Nv_QYpOcVIYhz8ihJ3kVgt5exyIGrdCBFvffgixfEMVf0PRPJWste4pFwEsbnhqjg3ik8GwZh5uLrSfT6bsg-XTO4tNDn9aU0R7JUeb7F6Df3YTRVg7hc2c2PAL_fwO2rWMn7CUqLZvoR2Koi855UkBohZKeG0R6O7x0MjiqwKqlTZBxYQwUFvG_WMEm-aeBnO6lY0SfgBIWRk2j3cwjJkxDdqwmyw8pmCdSqWnMSKEDisXw3IhHmyl9x_JYCoKJ5w3DQ3Nfdabyf5mT5PuXMK3PpiOE3rOTFZffsU_uweS9IPasFDlf6jhn4ohqzglzQ6r1w9wKV1tbH-VB2tuTKcTMQSGxTjcGUC5KUQaY9KB_7dbhde_jUpTk3FAcDmJlTbOX7cdp3Cup5bEbmlxLkXGKl-L9OgYomf5kIuuIkxgE_2NCkF_mVtEceqH3eDcyHq6HM8T-gwC15G-w3ZxiNo-BRdwk6n65aeSh-fley4GQCeQh78r2S_lfpyPIGgHZVv6isMOsFUcxnHjjZNn1ULxcN5MG_-NBVibscV_dIYTcU2YcZMke6IlWERtqfYiAUFgFc6y4p8q5vEYw6bqSaCR_IQBwTeWHYH2rwtcYR4Ld3uXj1p2Ts5QWD-DcviOEDXN0X3arXDk5ZPOpaw2p8wJaNfIe6qW6---38wua4_vHb4MC81229XC_KxPmCjcy_a4F8SbP4cGXpxxADq5yJk0wpMzGOa2Z66QDeznPmHGTCTZSLhsly7lNlRMIq8M6h73Qo3H_lxQhXkixptpX85x8tCN0HLAwp5X_sOxs8_dcQoVcxTlfO_uFoel7HhiRIAgEQ75Il9u6jRbSCAjBiveuoXAQX9VEUj45QEbqBcI8LxZxFq6gERcmzgJ9bjME7NNmjhepDtk6VuOJad3qclOrVlMOSoda-C3FAhsbk1t3nEDAgI3HVRNsSSJm-WfAHf7kEZfNAzCERz4hTX3QdI9x5L3FyC76OXcQK8svy0NwKxZSzgklWLp81MLaW1N71e_BIDLOVCcjnDuAVQvGgC_u1fJmDlEz12bNkQw9IRTv4cWv5VgeyYvUwolrTHujoXh2wPxbJnsYf-czODsB6jd_YmASrbZlEd96owTadsBNPIdRFM0TIteXPVHN7zElbcNlf3X6TXlLdaB4aY5KI-IqFcMMGoV56X_XIOh6k6pU3HATUq3G9VF_KZDVejcwjh_20eYvUfCVJ__hvw2Rxy-zZC4nuY6ZGAfR6Uh-uIJROFYiX_iG6YtNJYLqGfNqZp6DEAE8hidiHOviJSr1Yt3sNUI0r6HTRPWt8z9vMhSvk3fcVSneKCoEojkpTphTkDhFsr7YFYnrPBWhCRqBaHUugyAz_eQXkf860ZdJxpPPrKWgJt0B0iYkDsAfr5HEv0HlnfT4bMvqJRMbiO2LcmMKFfH9OSer6383cHubszgnLcLzoq3WNzG9uFG02PaRn441UZfPoYAlllwhHe9wJW3hJ84HScz7SXSBWGr-KFOgeHRhPMjGGeiNJ1DICdCKVM9BUyDt9zZT_stC5-oXolxvB_A0rxFSthXusA8fsQSfIqS_lyofKEgFoiuJ7pphYOpVcuzJdujJvbagublzmvatQhEj7TQ0IkxowVRbJvOufXcgZUzRs7bWfcAt7WOUv0C33TXbHenpd9KJy86bMBP5ZWJOSmf5WW2XQuqpkyv0rzByMt9FFZP-CoJme6XQDY0nYfa_IBMEd_zRvQ_hNl8jlD5lSmzp-XYBOcvvyXAAODfznImEa2teJ6IrqXK6dPyiErF9NUTwf-kSRMnXUn3fJcP2LrFwtwaP8O9_oBTtQ8RcZQo--zpWABh7fHuw0-hUYG04hBsJfdXj6CvS24U8cBCBXEcnYj74wkR6FMOsXqcABR7iMLbWLLB__8H0DyFlzEabTPmb9sNoqA_LE_HLxMDsq4SYqFxms_mboEtUvWAyo-4lVaIu-RDmKQXT7GyW0fZ-YDGW9j-Sb14B9F0xhY3XOE672ASQO0keCGESaiZwL0iEqflNtKAYySFaFM1yQNOultRQHQmxJ-nVoIwupcJlDECx9GQfwVwNAZZfkiXdtJgfeAi2w8vpf3lQw6lW0N6pdnu4f6dRqlvxSpTsYkKj7TaUIUx60uQ4JxJePsYwZh_h0KdGjLM9rmEET4EHnEXYTBsZR8FbqHzogXE7xrAu-7ZbCS_X0tjrQYjG3JMiwv-VRvI_6NAWqRcQ382GMOZuQwGVNB6zLn5z1G9zde0Ved9py3bBG9s_SmATmXWQ_CrmW2tf5xPoCqP6N62yEUD9ZEagXCsgRjpU0dzNdvE1D6FzB8ZeUe8vYQS90VfPNNmM-8lE4qsoLamTcOqHgzx6RhuarRfSOaohOFTfowIAj6Q0QG-5g5boRqDsLWUj912BAdWk7hNP7QH1DTLFzZTov2oYdfHZij4L87MK8#DSD"

@Composable
fun MainScreen(
    router: Router = Router()
) {
    LazyColumn {
        /*
         * Баннер promoted фильма
         * TODO заглушка, убрать её и реализовать с ViewModel
         */
        item {
            BannerView(router = router, imageUrl = exampleImageUrl)
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        /*
         * Избранное
         * TODO (ViewModel) если у пользователя нет фильмов в избранных, то список не должен показываться
         */
        item {
            Text(
                text = stringResource(R.string.favorites),
                style = H1,
                color = Accent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
        }
        item {
            // Поиск центрального элемента
            // https://stackoverflow.com/questions/71832396/jetpack-compose-lazylist-possible-to-zoom-the-center-item
            val lazyState = rememberLazyListState()
            val centerPosition by rememberLazyListFirstPosition(state = lazyState)

            LazyRow(
                modifier = Modifier
                    .height(172.dp),
                verticalAlignment = Alignment.CenterVertically,
                state = lazyState
            ) {
                // TODO заглушка, убрать её и реализовать с ViewModel
                items(10) {
                    FavoriteView(
                        imageUrl = exampleImageUrl,
                        selected = it == centerPosition
                    )
                }
                item { Spacer(modifier = Modifier.width(16.dp)) }
            }
        }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        /*
         * Галерея
         */
        item {
            Text(
                text = stringResource(R.string.gallery),
                style = H1,
                color = Accent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
        // TODO заглушка, убрать её и реализовать с ViewModel
        items(10) {
            MovieView(
                name = "Пираты карибского моря",
                imageUrl = exampleImageUrl,
                year = (Math.random() * 72 + 1950).toInt(),
                country = "США",
                genres = List((Math.random() * 19 + 1).toInt()) { "драма" },
                rating = (Math.random() * 9 + 1).toFloat()
            )
        }
    }
}

@Composable
private fun BannerView(
    router: Router,
    imageUrl: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        // Постер
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Background,
                        .25f to Color.Transparent,
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Смотреть
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .padding(bottom = 32.dp)
            ) {
                StyledButton(
                    onClick = {
                        router.routeTo(Screen.Movie) // Аргументы?
                        // TODO view model
                    },
                    text = stringResource(R.string.watch)
                )
            }
        }
    }
}

@Composable
private fun FavoriteView(
    imageUrl: String,
    selected: Boolean
) {
    val width by animateDpAsState(if (selected) 120.dp else 100.dp)
    val height by animateDpAsState(if (selected) 172.dp else 144.dp)

    Box(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width, height)
        ) {
            // Постер
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(Shapes.small)
                    .clickable { /* TODO ViewModel */ }
            )
            // Крестик
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_cross),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { /* TODO ViewModel */ }
                )
            }
        }
    }
}

@Composable
private fun MovieView(
    name: String,
    imageUrl: String,
    year: Int,
    country: String,
    genres: Iterable<String>, // TODO может подавать String во View, а список во ViewModel?
    rating: Float
) {
    val hue = (rating - 1) / 9 * 120
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .clickable { /* TODO ViewModel */ }
        ) {
            // Постер
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 144.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                // Название
                Text(
                    text = name,
                    style = H2,
                    color = BrightWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Год и страна производства
                Text(
                    text = "$year • $country",
                    style = BodySmall,
                    color = BrightWhite,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Жанры
                Text(
                    text = genres.joinToString(),
                    style = BodySmall,
                    color = BrightWhite,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                // Средняя оценка
                Box(
                    modifier = Modifier
                        .size(56.dp, 28.dp)
                        .background(
                            color = Color.hsv(hue, .99f, .67f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating.format(1),
                        style = Body,
                        color = BrightWhite
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MainScreen()
        }
    }
}

@Preview
@Composable
private fun MovieViewPreview() {
    MovieCatalogTheme {
        MovieView(
            name = "Пираты карибского моря. Проклятие черной жемчужины",
            imageUrl = exampleImageUrl,
            year = 1999,
            country = "США",
            genres = List(20) { "драма" },
            rating = 6.5f
        )
    }
}