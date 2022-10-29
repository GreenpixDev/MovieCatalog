package ru.greenpix.moviecatalog.ui.view.screen.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.view.screen.movie.model.MovieReview
import ru.greenpix.moviecatalog.ui.view.shared.model.ViewState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    // TODO ОДНОЗНАЧНО ПЕРЕДЕЛАТЬ! СДЕЛАТЬ ОТДЕЛЬНЫЕ МОДЕЛИ ДЛЯ РЕТРОФИТА, РЕПОЗИТОРИЯ И ВЬЮШЕК!
    private companion object {
        val VIEW_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    private val _loadState = mutableStateOf(ViewState.UNLOADED)
    private val _favoriteState = mutableStateOf(false)
    private val _nameState = mutableStateOf("")
    private val _movieImageUrlState = mutableStateOf("")
    private val _descriptionState = mutableStateOf("")
    private val _yearState = mutableStateOf(0)
    private val _countryState = mutableStateOf("")
    private val _durationState = mutableStateOf(0)
    private val _taglineState = mutableStateOf("")
    private val _producerState = mutableStateOf("")
    private val _budgetState = mutableStateOf(0)
    private val _feesState = mutableStateOf(0)
    private val _ageState = mutableStateOf(0)
    private val _genresState = mutableStateListOf<String>()
    private val _myReviewState = mutableStateOf<MovieReview?>(null)
    private val _otherReviewsState = mutableStateListOf<MovieReview>()

    private var movieId: String = ""

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
    val durationState: State<Int>
        get() = _durationState
    val taglineState: State<String>
        get() = _taglineState
    val producerState: State<String>
        get() = _producerState
    val budgetState: State<Int>
        get() = _budgetState
    val feesState: State<Int>
        get() = _feesState
    val ageState: State<Int>
        get() = _ageState
    val genresState: List<String>
        get() = _genresState
    val myReviewState: State<MovieReview?>
        get() = _myReviewState
    val otherReviewsState: List<MovieReview>
        get() = _otherReviewsState

    suspend fun load(movieId: String, isFavorite: Boolean) {
        if (this.loadState.value == ViewState.LOADED && this.movieId == movieId) {
            return
        }
        _loadState.value = ViewState.LOADING
        _genresState.clear()
        _otherReviewsState.clear()
        this.movieId = movieId

        val movie = movieRepository.getDetails(movieId)
        _favoriteState.value = isFavorite
        _nameState.value = movie.name ?: ""
        _movieImageUrlState.value = movie.poster ?: ""
        _descriptionState.value = movie.description ?: ""
        _yearState.value = movie.year
        _countryState.value = movie.country ?: ""
        _durationState.value = movie.time
        _taglineState.value = movie.tagline ?: ""
        _producerState.value = movie.director ?: ""
        _budgetState.value = movie.budget ?: -1
        _feesState.value = movie.fees ?: -1
        _ageState.value = movie.ageLimit
        _genresState.addAll(movie.genres.mapNotNull { it.name })
        _myReviewState.value = MovieReview(
            author = "Роман",
            anonymous = false,
            avatarUrl = "https://chudo-prirody.com/uploads/posts/2021-08/thumbs/1628944329_99-p-smeshnie-foto-kotikov-na-avu-102.jpg",
            comment = "Сразу скажу, что фильм мне понравился. Люблю Фримэна, уважаю Роббинса. Читаю Кинга. Но рецензия красненькая.",
            date = "07.10.2022",
            rating = 1
        )
        _otherReviewsState.addAll(
            movie.reviews.map { review ->
                MovieReview(
                    author = review.author?.nickName ?: "",
                    avatarUrl = review.author?.avatar ?: "",
                    comment = review.reviewText ?: "",
                    anonymous = review.isAnonymous,
                    date = LocalDateTime
                        .parse(review.createDateTime)
                        .toLocalDate()
                        .format(VIEW_FORMATTER),
                    rating = review.rating
                )
            }
        )
        _loadState.value = ViewState.LOADED
    }

    fun onToggleFavorite() = viewModelScope.launch {
        val newStatus = !_favoriteState.value
        try {
            if (newStatus) {
                favoriteRepository.addFavoriteMovie(movieId)
            }
            else {
                favoriteRepository.deleteFavoriteMovie(movieId)
            }
            _favoriteState.value = newStatus
        }
        catch (e: AuthorizationException) {
            // TODO перенаправляем на экран авторизации
        }
        catch (e: Exception) {
            e.printStackTrace()
            // TODO надо бы сделать обработку ошибок
        }
    }

    fun onDeleteReview() {
        // TODO запрос к репозиторию
        _myReviewState.value = null
    }
}