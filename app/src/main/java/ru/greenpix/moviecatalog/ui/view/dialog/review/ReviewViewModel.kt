package ru.greenpix.moviecatalog.ui.view.dialog.review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {

    private var _anonymousState = mutableStateOf(false)
    private var _commentState = mutableStateOf("")
    private var _ratingState = mutableStateOf(0)

    val anonymousState: State<Boolean>
        get() = _anonymousState
    val commentState: State<String>
        get() = _commentState
    val ratingState: State<Int>
        get() = _ratingState

    fun load(
        comment: String,
        rating: Int,
        anonymous: Boolean,
    ) {
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
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        onSuccess.invoke()
    }

}