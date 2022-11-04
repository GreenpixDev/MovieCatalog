package ru.greenpix.moviecatalog.ui.util

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

inline fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T?) -> Any)? = null,
    noinline contentType: (item: T?) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    items[it]?.let { item -> itemContent(item) }
}

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return this.itemCount == 0
}

fun <T : Any> LazyPagingItems<T>.firstOrNull(): T? {
    return if (isEmpty()) null else this[0]
}

fun <T : Any> LazyPagingItems<T>.isFirstLoading(): Boolean {
    return this.loadState.refresh == LoadState.Loading
}