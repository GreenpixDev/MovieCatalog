package ru.greenpix.moviecatalog.ui.navigation.view

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.ui.navigation.Destination
import ru.greenpix.moviecatalog.ui.theme.Accent
import ru.greenpix.moviecatalog.ui.theme.Footnote
import ru.greenpix.moviecatalog.ui.theme.GrayBokara
import ru.greenpix.moviecatalog.ui.theme.GraySilver

@Composable
fun MainNavigationScaffold(
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            MainNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content.invoke()
        }
    }
}

@Composable
fun MainNavigationBar(
    navController: NavController
) {
    BottomNavigation(
        backgroundColor = GrayBokara,
    ) {
        MainNavigationItem(
            navController = navController,
            route = Destination.Main.Gallery.route,
            painter = painterResource(id = R.drawable.ic_movie_gallery),
            text = stringResource(id = R.string.main),
        )
        MainNavigationItem(
            navController = navController,
            route = Destination.Main.Profile.route,
            painter = painterResource(id = R.drawable.ic_profile),
            text = stringResource(id = R.string.profile),
        )
    }
}

@Composable
private fun RowScope.MainNavigationItem(
    navController: NavController,
    route: String,
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
        selected = currentDestination?.hierarchy?.any { it.route == route } == true,
        selectedContentColor = Accent,
        unselectedContentColor = GraySilver,
        onClick = {
            navController.navigate(route) {
                popUpTo(Destination.Main.route) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}