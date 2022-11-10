package ru.greenpix.moviecatalog.ui.shared.view

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
import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.roundedAtEnd
import ru.greenpix.moviecatalog.ui.util.roundedAtStart

/**
 * Стилизированная под приложение поле ввода [пола][Gender].
 *
 * @param value пол ввода, который будет показан в поле ввода пола
 * @param onValueChange обратный вызов, который запускается, когда служба ввода обновляет пол. Обновленный пол приходит как параметр обратного вызова
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