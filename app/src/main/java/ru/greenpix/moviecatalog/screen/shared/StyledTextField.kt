package ru.greenpix.moviecatalog.screen.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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
            .clip(Shapes.small)
            .border(1.dp, GraySilver, Shapes.small),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
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