package ru.greenpix.moviecatalog.ui.navigation

sealed class Destination(
    private val name: String? = null
) {

    private val dest: String by lazy {
        name ?: javaClass.simpleName.lowercase()
    }

    val route: String by lazy {
        buildString {
            append(dest)
            args().forEach {
                append("/$it")
            }
        }
    }

    protected open fun args(): List<String> = emptyList()

    fun buildRoute(vararg args: Any): String = buildString {
        append(dest)
        args.forEach {
            append("/$it")
        }
    }

    object SignIn: Destination("sign-in")
    object SignUp: Destination("sign-up")
    object Main: Destination()
    object Profile: Destination()
    object Movie: Destination() {
        const val MOVIE_ID = "movieId"
        const val IS_FAVORITE = "isFavorite"
        override fun args(): List<String> = listOf(MOVIE_ID, IS_FAVORITE)
    }
    object Review: Destination()

}