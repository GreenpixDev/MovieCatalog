package ru.greenpix.moviecatalog.ui.screen.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.repository.ReviewRepository
import ru.greenpix.moviecatalog.ui.screen.movie.mapper.MovieReviewMapper
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieReviewModel
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieViewState
import ru.greenpix.moviecatalog.util.decodeJwt
import java.net.SocketException
import java.net.UnknownHostException

class MovieViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
    private val reviewRepository: ReviewRepository,
    private val jwtRepository: JwtRepository,
    private val gson: Gson,
    private val movieReviewMapper: MovieReviewMapper
) : ViewModel() {

    private val _viewState = mutableStateOf<MovieViewState>(MovieViewState.Loading)
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
    private val _myReviewState = mutableStateOf<MovieReviewModel?>(null)
    private val _myReviewDeletedState = mutableStateOf<Boolean>(false)
    private val _otherReviewsState = mutableStateListOf<MovieReviewModel>()

    private var movieId: String = ""

    val viewState: State<MovieViewState>
        get() = _viewState
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
    val myReviewState: State<MovieReviewModel?>
        get() = _myReviewState
    val myReviewDeletedState: State<Boolean>
        get() = _myReviewDeletedState
    val otherReviewsState: List<MovieReviewModel>
        get() = _otherReviewsState

    suspend fun load(movieId: String, isFavorite: Boolean) {
        if (this.viewState.value !is MovieViewState.Default || this.movieId != movieId) {
            _viewState.value = MovieViewState.Loading
            this.movieId = movieId
        }

        try {
            val movie = movieRepository.getDetails(movieId)
            val rawToken = jwtRepository.getToken()
            checkNotNull(rawToken) {"jwt token cannot be null"}
            val uniqueName = gson.decodeJwt(rawToken).uniqueName

            _genresState.clear()
            _otherReviewsState.clear()

            _favoriteState.value = isFavorite
            _nameState.value = movie.name ?: ""
            _movieImageUrlState.value = movie.posterUrl ?: ""
            _descriptionState.value = movie.description ?: ""
            _yearState.value = movie.year
            _countryState.value = movie.country ?: ""
            _durationState.value = movie.time
            _taglineState.value = movie.tagline ?: ""
            _producerState.value = movie.director ?: ""
            _budgetState.value = movie.budget ?: -1
            _feesState.value = movie.fees ?: -1
            _ageState.value = movie.ageLimit
            _genresState.addAll(movie.genres)
            _myReviewState.value = movie.reviews
                .find { it.author?.username == uniqueName }
                ?.let { movieReviewMapper.map(it) }
                ?.apply { _myReviewDeletedState.value = false }
            _otherReviewsState.addAll(movie.reviews
                .filter { it.author?.username != uniqueName }
                .map { movieReviewMapper.map(it) }
            )
            _viewState.value = MovieViewState.Default
        }
        catch (e: AuthorizationException) {
            _viewState.value = MovieViewState.AuthorizationFailed
        }
        catch (e: Exception) {
            _viewState.value = when(e) {
                is HttpException -> MovieViewState.HttpError
                is UnknownHostException, is SocketException -> MovieViewState.NetworkError
                else -> {
                    e.printStackTrace()
                    MovieViewState.UnknownError
                }
            }
        }
    }

    fun onToggleFavorite() = viewModelScope.launch {
        val newStatus = !favoriteState.value
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
            _viewState.value = MovieViewState.AuthorizationFailed
        }
        catch (e: Exception) {
            _viewState.value = when(e) {
                is HttpException -> MovieViewState.HttpError
                is UnknownHostException, is SocketException -> MovieViewState.NetworkError
                else -> {
                    e.printStackTrace()
                    MovieViewState.UnknownError
                }
            }
        }
    }

    fun onDeleteReview() {
        val reviewId = myReviewState.value?.id ?: return

        viewModelScope.launch {
            try {
                _myReviewDeletedState.value = true
                reviewRepository.deleteReview(movieId, reviewId)
            }
            catch (e: AuthorizationException) {
                _viewState.value = MovieViewState.AuthorizationFailed
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is HttpException -> MovieViewState.HttpError
                    is UnknownHostException, is SocketException -> MovieViewState.NetworkError
                    else -> {
                        e.printStackTrace()
                        MovieViewState.UnknownError
                    }
                }
            }
        }
    }
}