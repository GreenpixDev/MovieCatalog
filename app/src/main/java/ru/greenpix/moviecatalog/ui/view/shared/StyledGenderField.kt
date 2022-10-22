package ru.greenpix.moviecatalog.ui.view.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.roundedAtEnd
import ru.greenpix.moviecatalog.ui.util.roundedAtStart
import ru.greenpix.moviecatalog.ui.view.shared.model.Gender

/**
 * Поля выбора пола.
 * Если [value] true, то поле выберет мужской пол, в противном женский.
 */
@Composable
fun StyledGenderField(
    value: Gender,
    onValueChange: (Gender) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        // Мужской
        Button(
            onClick = { onValueChange.invoke(Gender.MALE) },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (value == Gender.MALE) Accent else Color.Transparent,
                contentColor = if (value == Gender.MALE) BaseWhite else GrayFaded,
            ),
            shape = Shapes.small.roundedAtStart(),
            border = BorderStroke(1.dp, GraySilver)
        ) {
            Text(
                text = stringResource(R.string.male),
                style = BodySmall,
            )
        }
        // Женский
        Button(
            onClick = { onValueChange.invoke(Gender.FEMALE) },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (value == Gender.FEMALE) Accent else Color.Transparent,
                contentColor = if (value == Gender.FEMALE) BaseWhite else GrayFaded,
            ),
            shape = Shapes.small.roundedAtEnd(),
            border = BorderStroke(1.dp, GraySilver)
        ) {
            Text(
                text = stringResource(R.string.female),
                style = BodySmall
            )
        }
    }
}