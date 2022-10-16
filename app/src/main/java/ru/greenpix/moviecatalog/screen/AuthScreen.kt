package ru.greenpix.moviecatalog.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.RouterHost
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.screen.auth.SignInScreen
import ru.greenpix.moviecatalog.screen.auth.SignUpScreen
import ru.greenpix.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun AuthScreen(
    router: Router = Router()
) {
    var logoScaled by remember { mutableStateOf(true) }
    val logoWidth by animateDpAsState(if (logoScaled) 250.dp else 147.41.dp)
    val logoHeight by animateDpAsState(if (logoScaled) 170.dp else 100.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.named_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
                .width(logoWidth)
                .height(logoHeight)
        )
        RouterHost(
            router = router,
            startDestination = Screen.Auth.SignIn,
            screens = mapOf(
                Screen.Auth.SignIn to {
                    logoScaled = true
                    SignInScreen(it)
                },
                Screen.Auth.SignUp to {
                    logoScaled = false
                    SignUpScreen(it)
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthPreview() {
    MovieCatalogTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AuthScreen()
        }
    }
}