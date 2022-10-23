package ru.greenpix.moviecatalog.ui.view.dialog.review

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.getViewModel
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.theme.*
import ru.greenpix.moviecatalog.ui.view.shared.StyledButton
import ru.greenpix.moviecatalog.ui.view.shared.StyledClickableText

@Composable
fun ReviewDialog(
    onDismissRequest: () -> Unit,
    initComment: String,
    initRating: Int,
    initAnonymous: Boolean,
    properties: DialogProperties = DialogProperties(),
    viewModel: ReviewViewModel = getViewModel()
) {
    val anonymous by remember { viewModel.anonymousState }
    val comment by remember { viewModel.commentState }
    val rating by remember { viewModel.ratingState }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        ReviewDialogContent(
            anonymous = anonymous,
            comment = comment,
            rating = rating,
            onAnonymousChange = viewModel::onAnonymousChange,
            onCommentChange = viewModel::onCommentChange,
            onRatingChange = viewModel::onRatingChange,
            onSave = {
                viewModel.onSave(
                    onSuccess = onDismissRequest,
                    onError = { /*TODO*/ }
                )
            },
            onCancel = onDismissRequest
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.load(
            comment = initComment,
            rating = initRating,
            anonymous = initAnonymous
        )
    })
}

@Composable
private fun ReviewDialogContent(
    anonymous: Boolean,
    comment: String,
    rating: Int,
    onAnonymousChange: (Boolean) -> Unit,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
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
                onValueChange = onRatingChange
            )
            CommentField(
                value = comment,
                onValueChange = onCommentChange
            )
            AnonymousField(
                value = anonymous,
                onValueChange = onAnonymousChange
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
        ReviewDialogContent(
            anonymous = true,
            comment = "",
            rating = 5,
            onAnonymousChange = {},
            onCommentChange = {},
            onRatingChange = {},
            onSave = {},
            onCancel = {}
        )
    }
}