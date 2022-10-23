package ru.greenpix.moviecatalog.ui.view.screen.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import ru.greenpix.moviecatalog.ui.view.screen.movie.model.MovieReview
import ru.greenpix.moviecatalog.ui.view.shared.model.ViewState

class MovieViewModel : ViewModel() {

    private val _loadState = mutableStateOf(ViewState.UNLOADED)
    private val _favoriteState = mutableStateOf(false)
    private val _nameState = mutableStateOf("")
    private val _movieImageUrlState = mutableStateOf("")
    private val _descriptionState = mutableStateOf("")
    private val _yearState = mutableStateOf(0)
    private val _countryState = mutableStateOf("")
    private val _durationState = mutableStateOf("")
    private val _taglineState = mutableStateOf("")
    private val _producerState = mutableStateOf("")
    private val _budgetState = mutableStateOf("")
    private val _feesState = mutableStateOf("")
    private val _ageState = mutableStateOf(0)
    private val _genresState = mutableStateListOf<String>()
    private val _myReviewState = mutableStateOf<MovieReview?>(null)
    private val _otherReviewsState = mutableStateListOf<MovieReview>()

    private var movieId: Int = 0

    val loadState: State<ViewState>
        get() = _loadState
    val favoriteState: State<Boolean>
        get() = _favoriteState
    val nameState: State<String>
        get() = _nameState
    val movieImageUrlState: State<String>
        get() = _movieImageUrlState
    val descriptionState: State<String>
        get() = _descriptionState
    val yearState: State<Int>
        get() = _yearState
    val countryState: State<String>
        get() = _countryState
    val durationState: State<String>
        get() = _durationState
    val taglineState: State<String>
        get() = _taglineState
    val producerState: State<String>
        get() = _producerState
    val budgetState: State<String>
        get() = _budgetState
    val feesState: State<String>
        get() = _feesState
    val ageState: State<Int>
        get() = _ageState
    val genresState: List<String>
        get() = _genresState
    val myReviewState: State<MovieReview?>
        get() = _myReviewState
    val otherReviewsState: List<MovieReview>
        get() = _otherReviewsState

    suspend fun load(movieId: Int) {
        if (this.loadState.value == ViewState.LOADED && this.movieId == movieId) {
            return
        }
        _loadState.value = ViewState.LOADING
        _genresState.clear()
        _otherReviewsState.clear()
        this.movieId = movieId
        // TODO получаем данные из репозитория (сейчас заглушка)
        delay(1000)
        _nameState.value = "Побег из Шоушенка"
        _movieImageUrlState.value = "https://kinopoisk-ru.clstorage.net/15D2S2r30/33deda7SK/7htRvK7qLcqB7zW47Pf5ERgu4sxJy-F4M37R8H0rzARd-esRa2vgXRtB0HIEO4I3Exzc257O-lDcQ0AMPsqM098SBI3_lmmv4LpMkcm72FBpLpOhGMOEWWpupb0IRrC4nVkjtKPzHP2b8FVrBTv3QmZ93Ir-__pt7BpNxNaawXp6CxEMfhagTpHbcKgw1ykRpG_2nH2W_Ug7c03ozQeoeEkFPxhLj-TdV2w1jw32S_ZKUbASRvv2kJjWHYQvjoUC1kf58MqGrL75X3w8ALPFtRxbTiBhonHsa-PRTOEbxIDt5YMYE4f4_RvoyfPp6hc6ki0ATpL37lQwksGIa7Ih6qoTscie5tBG_ft0zOxPUbGYS7qohdJJgJMHyUBVT2DY_eUvAAfnbEWf0MkTYXrX4mah9FpelyLctB7dIBPGYfJyb0HYOhog7tUvxDTMl32FuO-GJEGCRVyvDwE4UaMwzDVN5_QHgzRd3wTZ-zFqV2Ki8UAW4m_-DADqyQif0rXyRqMN5MJG3F6Nv_QYpOcVIYhz8ihJ3kVgt5exyIGrdCBFvffgixfEMVf0PRPJWste4pFwEsbnhqjg3ik8GwZh5uLrSfT6bsg-XTO4tNDn9aU0R7JUeb7F6Df3YTRVg7hc2c2PAL_fwO2rWMn7CUqLZvoR2Koi855UkBohZKeG0R6O7x0MjiqwKqlTZBxYQwUFvG_WMEm-aeBnO6lY0SfgBIWRk2j3cwjJkxDdqwmyw8pmCdSqWnMSKEDisXw3IhHmyl9x_JYCoKJ5w3DQ3Nfdabyf5mT5PuXMK3PpiOE3rOTFZffsU_uweS9IPasFDlf6jhn4ohqzglzQ6r1w9wKV1tbH-VB2tuTKcTMQSGxTjcGUC5KUQaY9KB_7dbhde_jUpTk3FAcDmJlTbOX7cdp3Cup5bEbmlxLkXGKl-L9OgYomf5kIuuIkxgE_2NCkF_mVtEceqH3eDcyHq6HM8T-gwC15G-w3ZxiNo-BRdwk6n65aeSh-fley4GQCeQh78r2S_lfpyPIGgHZVv6isMOsFUcxnHjjZNn1ULxcN5MG_-NBVibscV_dIYTcU2YcZMke6IlWERtqfYiAUFgFc6y4p8q5vEYw6bqSaCR_IQBwTeWHYH2rwtcYR4Ld3uXj1p2Ts5QWD-DcviOEDXN0X3arXDk5ZPOpaw2p8wJaNfIe6qW6---38wua4_vHb4MC81229XC_KxPmCjcy_a4F8SbP4cGXpxxADq5yJk0wpMzGOa2Z66QDeznPmHGTCTZSLhsly7lNlRMIq8M6h73Qo3H_lxQhXkixptpX85x8tCN0HLAwp5X_sOxs8_dcQoVcxTlfO_uFoel7HhiRIAgEQ75Il9u6jRbSCAjBiveuoXAQX9VEUj45QEbqBcI8LxZxFq6gERcmzgJ9bjME7NNmjhepDtk6VuOJad3qclOrVlMOSoda-C3FAhsbk1t3nEDAgI3HVRNsSSJm-WfAHf7kEZfNAzCERz4hTX3QdI9x5L3FyC76OXcQK8svy0NwKxZSzgklWLp81MLaW1N71e_BIDLOVCcjnDuAVQvGgC_u1fJmDlEz12bNkQw9IRTv4cWv5VgeyYvUwolrTHujoXh2wPxbJnsYf-czODsB6jd_YmASrbZlEd96owTadsBNPIdRFM0TIteXPVHN7zElbcNlf3X6TXlLdaB4aY5KI-IqFcMMGoV56X_XIOh6k6pU3HATUq3G9VF_KZDVejcwjh_20eYvUfCVJ__hvw2Rxy-zZC4nuY6ZGAfR6Uh-uIJROFYiX_iG6YtNJYLqGfNqZp6DEAE8hidiHOviJSr1Yt3sNUI0r6HTRPWt8z9vMhSvk3fcVSneKCoEojkpTphTkDhFsr7YFYnrPBWhCRqBaHUugyAz_eQXkf860ZdJxpPPrKWgJt0B0iYkDsAfr5HEv0HlnfT4bMvqJRMbiO2LcmMKFfH9OSer6383cHubszgnLcLzoq3WNzG9uFG02PaRn441UZfPoYAlllwhHe9wJW3hJ84HScz7SXSBWGr-KFOgeHRhPMjGGeiNJ1DICdCKVM9BUyDt9zZT_stC5-oXolxvB_A0rxFSthXusA8fsQSfIqS_lyofKEgFoiuJ7pphYOpVcuzJdujJvbagublzmvatQhEj7TQ0IkxowVRbJvOufXcgZUzRs7bWfcAt7WOUv0C33TXbHenpd9KJy86bMBP5ZWJOSmf5WW2XQuqpkyv0rzByMt9FFZP-CoJme6XQDY0nYfa_IBMEd_zRvQ_hNl8jlD5lSmzp-XYBOcvvyXAAODfznImEa2teJ6IrqXK6dPyiErF9NUTwf-kSRMnXUn3fJcP2LrFwtwaP8O9_oBTtQ8RcZQo--zpWABh7fHuw0-hUYG04hBsJfdXj6CvS24U8cBCBXEcnYj74wkR6FMOsXqcABR7iMLbWLLB__8H0DyFlzEabTPmb9sNoqA_LE_HLxMDsq4SYqFxms_mboEtUvWAyo-4lVaIu-RDmKQXT7GyW0fZ-YDGW9j-Sb14B9F0xhY3XOE672ASQO0keCGESaiZwL0iEqflNtKAYySFaFM1yQNOultRQHQmxJ-nVoIwupcJlDECx9GQfwVwNAZZfkiXdtJgfeAi2w8vpf3lQw6lW0N6pdnu4f6dRqlvxSpTsYkKj7TaUIUx60uQ4JxJePsYwZh_h0KdGjLM9rmEET4EHnEXYTBsZR8FbqHzogXE7xrAu-7ZbCS_X0tjrQYjG3JMiwv-VRvI_6NAWqRcQ382GMOZuQwGVNB6zLn5z1G9zde0Ved9py3bBG9s_SmATmXWQ_CrmW2tf5xPoCqP6N62yEUD9ZEagXCsgRjpU0dzNdvE1D6FzB8ZeUe8vYQS90VfPNNmM-8lE4qsoLamTcOqHgzx6RhuarRfSOaohOFTfowIAj6Q0QG-5g5boRqDsLWUj912BAdWk7hNP7QH1DTLFzZTov2oYdfHZij4L87MK8#DSD"
        _descriptionState.value = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения"
        _yearState.value = 1994
        _countryState.value = "США"
        _durationState.value = "142 мин."
        _taglineState.value = "«Страх - это кандалы. Надежда - это свобода»"
        _producerState.value = "Фрэнк Дарабонт"
        _budgetState.value = "\$25 000 000"
        _feesState.value = "\$28 418 687"
        _ageState.value = 16
        _genresState.addAll(listOf("драма", "боевик", "фантастика", "мелодрама"))
        _myReviewState.value = MovieReview(
            author = "Роман",
            anonymous = false,
            avatarUrl = "https://chudo-prirody.com/uploads/posts/2021-08/thumbs/1628944329_99-p-smeshnie-foto-kotikov-na-avu-102.jpg",
            comment = "Сразу скажу, что фильм мне понравился. Люблю Фримэна, уважаю Роббинса. Читаю Кинга. Но рецензия красненькая.",
            date = "07.10.2022",
            rating = 1
        )
        _otherReviewsState.addAll(List(9) {
            MovieReview(
                author = "Test #$it",
                avatarUrl = "",
                anonymous = true,
                comment = "Сразу скажу, что фильм мне понравился. Люблю Фримэна, уважаю Роббинса. Читаю Кинга. Но рецензия красненькая.",
                date = "07.10.2022",
                rating = it + 2
            )
        })
        _loadState.value = ViewState.LOADED
    }

    fun onToggleFavorite() {
        // TODO запрос к репозиторию
        _favoriteState.value = !_favoriteState.value
    }

    fun onDeleteReview() {
        // TODO запрос к репозиторию
        _myReviewState.value = null
    }
}