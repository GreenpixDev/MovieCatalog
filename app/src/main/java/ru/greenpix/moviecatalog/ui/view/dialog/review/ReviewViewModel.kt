package ru.greenpix.moviecatalog.ui.view.dialog.review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greenpix.moviecatalog.domain.ReviewModifyModel
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.ReviewRepository

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private var _anonymousState = mutableStateOf(false)
    private var _commentState = mutableStateOf("")
    private var _ratingState = mutableStateOf(0)

    private var movieId: String = "null"
    private var reviewId: String? = null

    val anonymousState: State<Boolean>
        get() = _anonymousState
    val commentState: State<String>
        get() = _commentState
    val ratingState: State<Int>
        get() = _ratingState

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
    }

    fun onRatingChange(rating: Int) {
        _ratingState.value = rating
    }

    fun onSave(
        onSuccess: () -> Unit
    ) {
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
                onSuccess.invoke()
            }
            catch (e: AuthorizationException) {
                // TODO перенаправляем на экран авторизации
            }
            catch (e: Exception) {
                // TODO надо бы сделать обработку ошибок
                e.printStackTrace()
            }
        }
    }

}