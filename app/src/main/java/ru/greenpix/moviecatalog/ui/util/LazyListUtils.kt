package ru.greenpix.moviecatalog.ui.util

import androidx.compose.foundation.lazy.*
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

val LazyListState.lastVisibleItemIndex: Int
    get() = this.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

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

inline fun <K, V> LazyListScope.items(
    items: Map<K, V>,
    noinline key: ((id: K, item: V) -> Any)? = null,
    noinline contentType: (id: K, item: V) -> Any? = { _, _ -> null },
    crossinline itemContent: @Composable LazyItemScope.(id: K, item: V) -> Unit
) = items(
    items = items.toList(),
    key = if (key != null) { pair: Pair<K, V> -> key(pair.first, pair.second) } else null,
    contentType = { pair: Pair<K, V> -> contentType(pair.first, pair.second) },
    itemContent = { pair: Pair<K, V> -> itemContent.invoke(this, pair.first, pair.second) }
)

inline fun <K, V> LazyListScope.itemsIndexed(
    items: Map<K, V>,
    noinline key: ((index: Int, id: K, item: V) -> Any)? = null,
    noinline contentType: (index: Int, id: K, item: V) -> Any? = { _, _, _ -> null },
    crossinline itemContent: @Composable LazyItemScope.(index: Int, id: K, item: V) -> Unit
) = itemsIndexed(
    items = items.toList(),
    key = if (key != null) { index: Int, pair: Pair<K, V> -> key(index, pair.first, pair.second) } else null,
    contentType = { index: Int, pair: Pair<K, V> -> contentType(index, pair.first, pair.second) },
    itemContent = { index: Int, pair: Pair<K, V> -> itemContent.invoke(this, index, pair.first, pair.second) }
)