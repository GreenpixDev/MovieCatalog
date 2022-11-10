package ru.greenpix.moviecatalog.ui.util

import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

/**
 * Функция, которая запоминает индекс первого элемента у [ленивого списка][LazyListState],
 * который полностью отображается на экране
 */
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

/**
 * Свойство получения индекса последнего видимого элемента в [ленивом списке][LazyListState]
 */
val LazyListState.lastVisibleItemIndex: Int
    get() = this.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

/**
 * Свойство получения прогресса прокрутки первого видимого элемента в [ленивом списке][LazyListState].
 *
 * Возвращает [Float] значение от 0 до 1 включительно.
 */
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

/**
 * Добавляет словарь предметов.
 *
 * @param items словарь данных
 * @param key фабрика стабильных и уникальных ключей, представляющих предмет.
 * Использование одного и того же ключа для нескольких элементов списка не допускается.
 * Тип ключа должен быть сохранен через Bundle на Android.
 * Если передано значение null, позиция в списке будет представлять ключ.
 * Когда вы указываете ключ, позиция прокрутки будет поддерживаться на основе ключа,
 * что означает, что если вы добавите элементы перед текущим видимым элементом,
 * элемент с данным ключом будет сохранен как первый видимый.
 * @param contentType фабрика типов контента для элемента.
 * Композиции элементов одного и того же типа могут быть повторно использованы более эффективно.
 * Обратите внимание, что null является допустимым типом, и элементы такого типа будут считаться совместимыми.
 * @param itemContent содержимое, отображаемое одним элементом
 */
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

/**
 * Добавляет словарь предметов, где содержимое элемента знает свой индекс.
 *
 * @param items словарь данных
 * @param key фабрика стабильных и уникальных ключей, представляющих предмет.
 * Использование одного и того же ключа для нескольких элементов списка не допускается.
 * Тип ключа должен быть сохранен через Bundle на Android.
 * Если передано значение null, позиция в списке будет представлять ключ.
 * Когда вы указываете ключ, позиция прокрутки будет поддерживаться на основе ключа,
 * что означает, что если вы добавите элементы перед текущим видимым элементом,
 * элемент с данным ключом будет сохранен как первый видимый.
 * @param contentType фабрика типов контента для элемента.
 * Композиции элементов одного и того же типа могут быть повторно использованы более эффективно.
 * Обратите внимание, что null является допустимым типом, и элементы такого типа будут считаться совместимыми.
 * @param itemContent содержимое, отображаемое одним элементом
 */
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