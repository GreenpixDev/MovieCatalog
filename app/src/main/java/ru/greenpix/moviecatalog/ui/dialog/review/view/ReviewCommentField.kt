package ru.greenpix.moviecatalog.ui.dialog.review.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun ReviewCommentField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = BrightWhite,
        contentColor = SealBrown,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = BodySmall,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.review),
                    style = BodyMontserrat,
                    color = Gray,
                    fontSize = 13.7.sp
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = SealBrown,
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            )
        )
    }
}