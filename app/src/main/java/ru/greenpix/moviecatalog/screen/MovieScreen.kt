package ru.greenpix.moviecatalog.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun MovieScreen(
    router: Router = Router()
) {
    Column {
        Text(text = "Movie")
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { router.routeTo(Screen.Home) }) {
            Text(text = "Домой")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePreview() {
    MovieCatalogTheme {
        MovieScreen()
    }
}