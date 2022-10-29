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
                append("/{$it}")
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

    object Auth : Destination() {
        object SignIn: Destination("sign-in")
        object SignUp: Destination("sign-up")
    }
    object Main : Destination() {
        object Gallery: Destination()
        object Profile: Destination()
    }
    object Movie: Destination() {
        const val MOVIE_ID = "movieId"
        const val IS_FAVORITE = "isFavorite"
        override fun args(): List<String> = listOf(MOVIE_ID, IS_FAVORITE)
    }
    object Review : Destination() {
        object Add : Destination() {
            const val MOVIE_ID = "movieId"
            override fun args(): List<String> = listOf(MOVIE_ID)
        }
        object Edit : Destination() {
            const val MOVIE_ID = "movieId"
            const val REVIEW_ID = "reviewId"
            const val COMMENT = "comment"
            const val RATING = "rating"
            const val IS_ANONYMOUS = "isAnonymous"
            override fun args(): List<String> = listOf(MOVIE_ID, REVIEW_ID, COMMENT, RATING, IS_ANONYMOUS)
        }
    }

}