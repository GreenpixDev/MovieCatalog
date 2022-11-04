package ru.greenpix.moviecatalog.ui.view.dialog.review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.domain.ReviewModifyModel
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.ReviewRepository
import ru.greenpix.moviecatalog.ui.view.dialog.review.model.ReviewViewState
import java.net.SocketException
import java.net.UnknownHostException

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private var _viewState = mutableStateOf<ReviewViewState>(ReviewViewState.Default)
    private var _anonymousState = mutableStateOf(false)
    private var _commentState = mutableStateOf("")
    private var _ratingState = mutableStateOf(0)
    private val _canSaveState = mutableStateOf(false)

    private var movieId: String = ""
    private var reviewId: String? = null

    val viewState: State<ReviewViewState>
        get() = _viewState
    val anonymousState: State<Boolean>
        get() = _anonymousState
    val commentState: State<String>
        get() = _commentState
    val ratingState: State<Int>
        get() = _ratingState
    val canSaveState: State<Boolean>
        get() = _canSaveState

    fun load(
        movieId: String,
        reviewId: String?,
        comment: String,
        rating: Int,
        anonymous: Boolean,
    ) {
        this.movieId = movieId
        this.reviewId = reviewId
        _commentState.value = comment
        _ratingState.value = rating
        _anonymousState.value = anonymous
    }

    fun onAnonymousChange(anonymous: Boolean) {
        _anonymousState.value = anonymous
    }

    fun onCommentChange(comment: String) {
        _commentState.value = comment
        validate()
    }

    fun onRatingChange(rating: Int) {
        _ratingState.value = rating
        validate()
    }

    fun onSave() {
        val reviewId = this.reviewId
        val reviewModel = ReviewModifyModel(
            reviewText = commentState.value,
            rating = ratingState.value,
            isAnonymous = anonymousState.value
        )

        viewModelScope.launch {
            try {
                if (reviewId == null) {
                    reviewRepository.addReview(movieId, reviewModel)
                }
                else {
                    reviewRepository.updateReview(movieId, reviewId, reviewModel)
                }
                _viewState.value = ReviewViewState.Saved
            }
            catch (e: AuthorizationException) {
                _viewState.value = ReviewViewState.AuthorizationFailed
            }
            catch (e: Exception) {
                _viewState.value = when(e) {
                    is HttpException -> ReviewViewState.HttpError
                    is UnknownHostException, is SocketException -> ReviewViewState.NetworkError
                    else -> {
                        e.printStackTrace()
                        ReviewViewState.UnknownError
                    }
                }
            }
        }
    }

    private fun validate() {
        _canSaveState.value = commentState.value.isNotBlank()
                && ratingState.value in 1..10
    }
}