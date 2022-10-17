package ru.greenpix.moviecatalog.util.compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberLazyListFirstPosition(state: LazyListState): State<Int> {
    return remember {
        derivedStateOf {
            val itemIndex = state.firstVisibleItemIndex
            val startOffset = state.firstVisibleItemScrollOffset
            itemIndex + if (startOffset > 0) 1 else 0
        }
    }
}