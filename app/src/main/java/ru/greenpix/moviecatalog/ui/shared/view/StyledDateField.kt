package ru.greenpix.moviecatalog.ui.shared.view

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.noRippleClickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

/**
 * Стилизированная под приложение поле ввода [даты][LocalDate].
 *
 * @param value дата ввода, которая будет показана в поле ввода даты
 * @param onValueChange обратный вызов, который запускается, когда служба ввода обновляет дату. Обновленная дата приходит как параметр обратного вызова
 */
@Composable
fun StyledDateField(
    value: LocalDate?,
    onValueChange: (LocalDate) -> Unit,
) {
    val defaultPickerDate = value ?: LocalDate.now()
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange.invoke(LocalDate.of(year, month + 1, dayOfMonth))
        },
        defaultPickerDate.year,
        defaultPickerDate.monthValue - 1,
        defaultPickerDate.dayOfMonth
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(Shapes.small)
            .border(1.dp, GraySilver, Shapes.small)
            .noRippleClickable { datePickerDialog.show() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = if (value == null) stringResource(R.string.birthday) else formatter.format(value),
            style = BodySmall,
            color = if (value == null) GrayFaded else Accent,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}
