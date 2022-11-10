package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.BodyMontserrat
import ru.greenpix.moviecatalog.ui.theme.BrightWhite
import ru.greenpix.moviecatalog.ui.theme.GraySilver
import ru.greenpix.moviecatalog.util.formatGrouped

private const val WEIGHT_COLUMN_KEY = .3125f
private const val WEIGHT_COLUMN_VALUE = 1 - WEIGHT_COLUMN_KEY

@Composable
fun MovieInfo(
    year: Int,
    country: String,
    duration: Int,
    tagline: String,
    producer: String,
    budget: Int,
    fees: Int,
    age: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        MovieSubtitle(text = stringResource(R.string.about_movie))
        TableRow(key = stringResource(R.string.year), value = year.toString())
        if (country.isNotBlank()) {
            TableRow(key = stringResource(R.string.country), value = country)
        }
        TableRow(key = stringResource(R.string.duration), value = "$duration мин.")
        if (tagline.isNotBlank()) {
            TableRow(key = stringResource(R.string.tagline), value = "«$tagline»")
        }
        if (producer.isNotBlank()) {
            TableRow(key = stringResource(R.string.producer), value = producer)
        }
        if (budget >= 0) {
            TableRow(key = stringResource(R.string.budget), value = "\$${budget.formatGrouped()}")
        }
        if (fees >= 0) {
            TableRow(key = stringResource(R.string.fees_in_the_world), value = "\$${fees.formatGrouped()}")
        }
        TableRow(key = stringResource(R.string.age), value = "$age+")
    }
}

@Composable
private fun TableRow(
    key: String,
    value: String
) {
    Row {
        Text(
            text = key,
            style = BodyMontserrat,
            color = GraySilver,
            modifier = Modifier.weight(WEIGHT_COLUMN_KEY)
        )
        Text(
            text = value,
            style = BodyMontserrat,
            color = BrightWhite,
            modifier = Modifier.weight(WEIGHT_COLUMN_VALUE)
        )
    }
}