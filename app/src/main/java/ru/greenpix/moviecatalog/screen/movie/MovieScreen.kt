package ru.greenpix.moviecatalog.screen.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.util.compose.roundedAtBottom

private const val exampleName = "Побег из Шоушенка"
private const val exampleDescription = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения"
private const val exampleImageUrl = "https://kinopoisk-ru.clstorage.net/15D2S2r30/33deda7SK/7htRvK7qLcqB7zW47Pf5ERgu4sxJy-F4M37R8H0rzARd-esRa2vgXRtB0HIEO4I3Exzc257O-lDcQ0AMPsqM098SBI3_lmmv4LpMkcm72FBpLpOhGMOEWWpupb0IRrC4nVkjtKPzHP2b8FVrBTv3QmZ93Ir-__pt7BpNxNaawXp6CxEMfhagTpHbcKgw1ykRpG_2nH2W_Ug7c03ozQeoeEkFPxhLj-TdV2w1jw32S_ZKUbASRvv2kJjWHYQvjoUC1kf58MqGrL75X3w8ALPFtRxbTiBhonHsa-PRTOEbxIDt5YMYE4f4_RvoyfPp6hc6ki0ATpL37lQwksGIa7Ih6qoTscie5tBG_ft0zOxPUbGYS7qohdJJgJMHyUBVT2DY_eUvAAfnbEWf0MkTYXrX4mah9FpelyLctB7dIBPGYfJyb0HYOhog7tUvxDTMl32FuO-GJEGCRVyvDwE4UaMwzDVN5_QHgzRd3wTZ-zFqV2Ki8UAW4m_-DADqyQif0rXyRqMN5MJG3F6Nv_QYpOcVIYhz8ihJ3kVgt5exyIGrdCBFvffgixfEMVf0PRPJWste4pFwEsbnhqjg3ik8GwZh5uLrSfT6bsg-XTO4tNDn9aU0R7JUeb7F6Df3YTRVg7hc2c2PAL_fwO2rWMn7CUqLZvoR2Koi855UkBohZKeG0R6O7x0MjiqwKqlTZBxYQwUFvG_WMEm-aeBnO6lY0SfgBIWRk2j3cwjJkxDdqwmyw8pmCdSqWnMSKEDisXw3IhHmyl9x_JYCoKJ5w3DQ3Nfdabyf5mT5PuXMK3PpiOE3rOTFZffsU_uweS9IPasFDlf6jhn4ohqzglzQ6r1w9wKV1tbH-VB2tuTKcTMQSGxTjcGUC5KUQaY9KB_7dbhde_jUpTk3FAcDmJlTbOX7cdp3Cup5bEbmlxLkXGKl-L9OgYomf5kIuuIkxgE_2NCkF_mVtEceqH3eDcyHq6HM8T-gwC15G-w3ZxiNo-BRdwk6n65aeSh-fley4GQCeQh78r2S_lfpyPIGgHZVv6isMOsFUcxnHjjZNn1ULxcN5MG_-NBVibscV_dIYTcU2YcZMke6IlWERtqfYiAUFgFc6y4p8q5vEYw6bqSaCR_IQBwTeWHYH2rwtcYR4Ld3uXj1p2Ts5QWD-DcviOEDXN0X3arXDk5ZPOpaw2p8wJaNfIe6qW6---38wua4_vHb4MC81229XC_KxPmCjcy_a4F8SbP4cGXpxxADq5yJk0wpMzGOa2Z66QDeznPmHGTCTZSLhsly7lNlRMIq8M6h73Qo3H_lxQhXkixptpX85x8tCN0HLAwp5X_sOxs8_dcQoVcxTlfO_uFoel7HhiRIAgEQ75Il9u6jRbSCAjBiveuoXAQX9VEUj45QEbqBcI8LxZxFq6gERcmzgJ9bjME7NNmjhepDtk6VuOJad3qclOrVlMOSoda-C3FAhsbk1t3nEDAgI3HVRNsSSJm-WfAHf7kEZfNAzCERz4hTX3QdI9x5L3FyC76OXcQK8svy0NwKxZSzgklWLp81MLaW1N71e_BIDLOVCcjnDuAVQvGgC_u1fJmDlEz12bNkQw9IRTv4cWv5VgeyYvUwolrTHujoXh2wPxbJnsYf-czODsB6jd_YmASrbZlEd96owTadsBNPIdRFM0TIteXPVHN7zElbcNlf3X6TXlLdaB4aY5KI-IqFcMMGoV56X_XIOh6k6pU3HATUq3G9VF_KZDVejcwjh_20eYvUfCVJ__hvw2Rxy-zZC4nuY6ZGAfR6Uh-uIJROFYiX_iG6YtNJYLqGfNqZp6DEAE8hidiHOviJSr1Yt3sNUI0r6HTRPWt8z9vMhSvk3fcVSneKCoEojkpTphTkDhFsr7YFYnrPBWhCRqBaHUugyAz_eQXkf860ZdJxpPPrKWgJt0B0iYkDsAfr5HEv0HlnfT4bMvqJRMbiO2LcmMKFfH9OSer6383cHubszgnLcLzoq3WNzG9uFG02PaRn441UZfPoYAlllwhHe9wJW3hJ84HScz7SXSBWGr-KFOgeHRhPMjGGeiNJ1DICdCKVM9BUyDt9zZT_stC5-oXolxvB_A0rxFSthXusA8fsQSfIqS_lyofKEgFoiuJ7pphYOpVcuzJdujJvbagublzmvatQhEj7TQ0IkxowVRbJvOufXcgZUzRs7bWfcAt7WOUv0C33TXbHenpd9KJy86bMBP5ZWJOSmf5WW2XQuqpkyv0rzByMt9FFZP-CoJme6XQDY0nYfa_IBMEd_zRvQ_hNl8jlD5lSmzp-XYBOcvvyXAAODfznImEa2teJ6IrqXK6dPyiErF9NUTwf-kSRMnXUn3fJcP2LrFwtwaP8O9_oBTtQ8RcZQo--zpWABh7fHuw0-hUYG04hBsJfdXj6CvS24U8cBCBXEcnYj74wkR6FMOsXqcABR7iMLbWLLB__8H0DyFlzEabTPmb9sNoqA_LE_HLxMDsq4SYqFxms_mboEtUvWAyo-4lVaIu-RDmKQXT7GyW0fZ-YDGW9j-Sb14B9F0xhY3XOE672ASQO0keCGESaiZwL0iEqflNtKAYySFaFM1yQNOultRQHQmxJ-nVoIwupcJlDECx9GQfwVwNAZZfkiXdtJgfeAi2w8vpf3lQw6lW0N6pdnu4f6dRqlvxSpTsYkKj7TaUIUx60uQ4JxJePsYwZh_h0KdGjLM9rmEET4EHnEXYTBsZR8FbqHzogXE7xrAu-7ZbCS_X0tjrQYjG3JMiwv-VRvI_6NAWqRcQ382GMOZuQwGVNB6zLn5z1G9zde0Ved9py3bBG9s_SmATmXWQ_CrmW2tf5xPoCqP6N62yEUD9ZEagXCsgRjpU0dzNdvE1D6FzB8ZeUe8vYQS90VfPNNmM-8lE4qsoLamTcOqHgzx6RhuarRfSOaohOFTfowIAj6Q0QG-5g5boRqDsLWUj912BAdWk7hNP7QH1DTLFzZTov2oYdfHZij4L87MK8#DSD"

private const val weightColumn1 = .3125f
private const val weightColumn2 = 1 - weightColumn1

@Composable
fun MovieScreen(
    router: Router = Router()
) {
    LazyColumn {
        item { BannerView(exampleName) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { DescriptionView(exampleDescription) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { AboutMovieView() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { GenresView() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ReviewsSubtitleView() }
    }
}

@Composable
private fun BannerView(
    name: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.7.dp)
            .clip(RoundedCornerShape(16.dp).roundedAtBottom())
    ) {
        // Постер
        AsyncImage(
            model = exampleImageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
               .fillMaxSize()
        )
        // Градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Black,
                        1f to Color.Transparent,
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f,
                    )
                )
        )
        // Название фильма
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                style = Title,
                color = BrightWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun DescriptionView(
    description: String
) {
    Text(
        text = description,
        style = BodySmall,
        color = BrightWhite,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun AboutMovieView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Subtitle(text = stringResource(R.string.about_movie))
        TableRowView(
            key = stringResource(R.string.year),
            value = "1994"
        )
        TableRowView(
            key = stringResource(R.string.country),
            value = "США"
        )
        TableRowView(
            key = stringResource(R.string.duration),
            value = "142 мин."
        )
        TableRowView(
            key = stringResource(R.string.tagline),
            value = "«Страх - это кандалы. Надежда - это свобода»"
        )
        TableRowView(
            key = stringResource(R.string.producer),
            value = "Фрэнк Дарабонт"
        )
        TableRowView(
            key = stringResource(R.string.budget),
            value = "\$25 000 000"
        )
        TableRowView(
            key = stringResource(R.string.fees_in_the_world),
            value = "\$28 418 687"
        )
        TableRowView(
            key = stringResource(R.string.age),
            value = "16+"
        )
    }
}

@Composable
private fun GenresView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Subtitle(text = stringResource(R.string.genres))
    }
}

@Composable
private fun ReviewsSubtitleView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Subtitle(text = stringResource(R.string.reviews))
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { /*TODO*/ }
        )
    }
}

@Composable
private fun ReviewView() {

}

@Composable
private fun TableRowView(
    key: String,
    value: String
) {
    Row {
        Text(
            text = key,
            style = BodySmall,
            color = Gray,
            modifier = Modifier.weight(weightColumn1)
        )
        Text(
            text = value,
            style = BodySmall,
            color = BrightWhite,
            modifier = Modifier.weight(weightColumn2)
        )
    }
}

@Composable
private fun Subtitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Body,
        color = BrightWhite,
        modifier = modifier.padding(bottom = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieScreenPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            MovieScreen()
        }
    }
}