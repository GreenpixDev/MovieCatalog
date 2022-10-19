package ru.greenpix.moviecatalog.ui.util

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

fun LazyListState.firstItemScrollProgress(): Float {
    val itemsInfo = layoutInfo.visibleItemsInfo
    if (itemsInfo.isEmpty()) {
        return 0f
    }
    if (firstVisibleItemIndex >= itemsInfo.size) {
        return 1f
    }
    val itemInfo = itemsInfo[firstVisibleItemIndex]
    if (itemInfo.index == 0) {
        return -itemInfo.offset / itemInfo.size.toFloat()
    }
    return 1f
}