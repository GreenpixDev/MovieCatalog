package ru.greenpix.moviecatalog.ui.screen.movie.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.shared.view.Avatar
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun MovieReview(
    isAnonymous: Boolean,
    author: String,
    avatarUrl: String,
    comment: String,
    date: String,
    rating: Int,
    hue: Float,
    isMine: Boolean = false,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .border(1.dp, GrayFaded, Shapes.small)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Аватар, имя пользователя и оценка
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Avatar(
                    url = if (isAnonymous) "" else avatarUrl,
                    modifier = Modifier.size(40.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isAnonymous) stringResource(id = R.string.anonymous_review) else author,
                        style = Body,
                        color = BrightWhite
                    )
                    if (isMine) {
                        Text(
                            text = stringResource(R.string.my_review),
                            style = BodyVerySmall,
                            color = GraySilver
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(42.dp, 28.dp)
                        .background(
                            color = Color.hsv(hue, .99f, .67f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rating.toString(),
                        style = Body,
                        color = BrightWhite
                    )
                }
            }
            // Описание
            Text(
                text = comment,
                style = BodySmall,
                color = BrightWhite
            )
            // Дата и кнопочки редактирования и удаления
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = date,
                    style = BodyVerySmall,
                    color = GraySilver
                )
                if (isMine) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onEdit)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onDelete)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieReviewPreview() {
    MovieCatalogTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            MovieReview(
                isAnonymous = false,
                author = "Роман",
                avatarUrl = "",
                comment = "Сразу скажу, что фильм мне понравился. Люблю Фримэна, уважаю Роббинса. Читаю Кинга. Но рецензия красненькая.",
                date = "07.10.2022",
                rating = 1,
                hue = 0f,
                isMine = true
            )
        }
    }
}