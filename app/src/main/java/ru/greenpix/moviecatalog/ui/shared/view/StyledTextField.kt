package ru.greenpix.moviecatalog.ui.shared.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.util.clearFocusOnKeyboardDismiss

/**
 * Стилизированная под приложение поле ввода текста.
 *
 * @param value текст ввода, который будет показан в поле ввода текста
 * @param onValueChange обратный вызов, который запускается, когда служба ввода обновляет текст. Обновленный текст приходит как параметр обратного вызова
 */
@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    // Содержит последнее внутреннее состояние TextFieldValue.
    // Нам нужно сохранить его, чтобы иметь правильное значение композиции.
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ))
    }
    // Содержит последнее значение TextFieldValue, с которым был перекомпонован BasicTextField.
    // Мы не могли просто передать `TextFieldValue(text = value)` в ExtendedTextField,
    // потому что нам нужно сохранить композицию.
    val textFieldValue = textFieldValueState.copy(text = value)
    // Последнее строковое значение, с которым любое текстовое поле было перекомпоновано или обновлено в обратном вызове onValueChange.
    // Мы отслеживаем это, чтобы предотвратить вызов onValueChange(String) для одной и той же строки,
    // когда onValueChange ExtendedTextField вызывается несколько раз без промежуточной перекомпоновки.
    var lastTextValue by remember(value) { mutableStateOf(value) }

    ExtendedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it

            val stringChangedSinceLastInvocation = lastTextValue != it.text
            lastTextValue = it.text

            if (stringChangedSinceLastInvocation) {
                onValueChange(it.text)
            }
        },
        singleLine = true,
        textStyle = BodySmall,
        placeholder = {
            Text(
                text = placeholderText,
                style = BodySmall,
                color = GrayFaded
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Accent,
            focusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(Shapes.small)
            .border(1.dp, GraySilver, Shapes.small)
            .clearFocusOnKeyboardDismiss(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
}

@Preview
@Composable
private fun StyledClickableTextPreview() {
    MovieCatalogTheme {
        StyledTextField(
            value = "",
            onValueChange = {},
            placeholderText = "Example"
        )
    }
}