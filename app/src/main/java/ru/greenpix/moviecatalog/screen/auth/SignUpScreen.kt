package ru.greenpix.moviecatalog.screen.auth

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
fun SignUpScreen(
    router: Router = Router()
) {
    Column {
        Text(text = "SignUp")
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            router.routeTo(Screen.Auth.SignIn)
        }) {
            Text(text = "У меня уже есть аккаунт")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    MovieCatalogTheme {
        SignUpScreen()
    }
}