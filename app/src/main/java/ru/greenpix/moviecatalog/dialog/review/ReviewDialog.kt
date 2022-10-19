package ru.greenpix.moviecatalog.dialog.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.screen.shared.StyledButton
import ru.greenpix.moviecatalog.screen.shared.StyledClickableText
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun ReviewDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        ReviewDialogContent(
            onSave = { /*TODO*/ },
            onCancel = onDismissRequest
        )
    }
}

@Composable
private fun ReviewDialogContent(
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    // TODO заглушка, вынести во ViewModel
    var anonymous by remember { mutableStateOf(true) }
    // TODO заглушка, вынести во ViewModel
    var comment by remember { mutableStateOf("") }
    // TODO заглушка, вынести во ViewModel
    var rating by remember { mutableStateOf(0) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = GrayNero,
        contentColor = BrightWhite
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.post_review),
                style = H1
            )
            RatingField(
                value = rating,
                onValueChange = { rating = it }
            )
            CommentField(
                value = comment,
                onValueChange = { comment = it }
            )
            AnonymousField(
                value = anonymous,
                onValueChange = { anonymous = it }
            )
            Buttons(
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}

@Composable
private fun RatingField(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 1..10) {
            StarImage(
                filled = i <= value,
                onClick = { onValueChange.invoke(i) }
            )
        }
    }
}

@Composable
private fun CommentField(
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

@Composable
private fun AnonymousField(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.anonymous_review),
            style = Body,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(Shapes.medium)
                .border(1.dp, GrayFaded, Shapes.medium)
                .clickable { onValueChange.invoke(!value) },
            contentAlignment = Alignment.Center
        ) {
            if (value) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun Buttons(
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StyledButton(
           onClick = onSave,
           text = stringResource(id = R.string.save)
        )
        StyledClickableText(
            onClick = onCancel,
            text = stringResource(R.string.cancel)
        )
    }
}

@Composable
private fun StarImage(
    filled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(3.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = if (filled) {
                painterResource(id = R.drawable.ic_filled_star)
            } else {
                painterResource(id = R.drawable.ic_empty_star)
            },
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun ReviewDialogPreview() {
    MovieCatalogTheme {
        ReviewDialog(onDismissRequest = {})
    }
}