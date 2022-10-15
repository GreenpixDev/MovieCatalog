package ru.greenpix.moviecatalog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Router {

    private val screenToNavController: MutableMap<Screen, NavController> = mutableMapOf()

    fun routeTo(screen: Screen) {
        screenToNavController[screen]?.navigate(screen.route)
    }

    fun routeTo(screen: Screen, builder: NavOptionsBuilder.() -> Unit) {
        screenToNavController[screen]?.navigate(screen.route, builder)
    }

    fun registerScreen(screen: Screen, navController: NavController) {
        screenToNavController[screen] = navController
    }
}

@Composable
fun RouterHost(router: Router, startDestination: Screen, screens: Map<Screen, @Composable (Router) -> Unit>) {
    val navController = rememberNavController()

    screens.forEach { router.registerScreen(it.key, navController) }

    NavHost(navController = navController, startDestination = startDestination.route) {
        screens.forEach { screen ->
            composable(screen.key.route) {
                screen.value.invoke(router)
            }
        }
    }
}