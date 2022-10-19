package ru.greenpix.moviecatalog.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.navigation.Router
import ru.greenpix.moviecatalog.navigation.RouterHost
import ru.greenpix.moviecatalog.navigation.Screen
import ru.greenpix.moviecatalog.screen.home.main.MainScreen
import ru.greenpix.moviecatalog.screen.home.profile.ProfileScreen
import ru.greenpix.moviecatalog.ui.theme.*

@Composable
fun HomeScreen(
    router: Router = Router()
) {
    val navController = rememberNavController()

    // Элементы кода позаимствованы с официальной документации
    // https://developer.android.com/jetpack/compose/navigation
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = GrayBokara,
            ) {
                BottomHomeNavigationItem(
                    navController = navController,
                    router = router,
                    screen = Screen.Home.Main,
                    painter = painterResource(id = R.drawable.ic_movie_gallery),
                    text = stringResource(id = R.string.main),
                )
                BottomHomeNavigationItem(
                    navController = navController,
                    router = router,
                    screen = Screen.Home.Profile,
                    painter = painterResource(id = R.drawable.ic_profile),
                    text = stringResource(id = R.string.profile),
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            RouterHost(
                router = router,
                startDestination = Screen.Home.Main,
                screens = mapOf(
                    Screen.Home.Main to { MainScreen(it) },
                    Screen.Home.Profile to { ProfileScreen(it) }
                ),
                navController = navController
            )
        }
    }
}

@Composable
private fun RowScope.BottomHomeNavigationItem(
    navController: NavController,
    router: Router,
    screen: Screen,
    painter: Painter,
    text: String
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigationItem(
        icon = {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painter,
                    contentDescription = null
                )
            }
        },
        label = {
            Text(
                text = text,
                style = Footnote
            )
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        selectedContentColor = Accent,
        unselectedContentColor = GraySilver,
        onClick = {
            router.routeTo(screen) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MovieCatalogTheme {
        HomeScreen()
    }
}