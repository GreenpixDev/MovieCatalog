package ru.greenpix.moviecatalog.ui.screen.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.ui.shared.view.Avatar
import ru.greenpix.moviecatalog.ui.theme.BrightWhite
import ru.greenpix.moviecatalog.ui.theme.H1

@Composable
fun ProfileHeader(
    login: String,
    avatarUrl: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Avatar(
            url = avatarUrl,
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = login,
            style = H1,
            color = BrightWhite
        )
    }
}