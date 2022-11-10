package ru.greenpix.moviecatalog.ui.util

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Функция проверки пустоты [ленивой страницы][LazyPagingItems].
 * Возвращает true, если страница пустая.
 */
fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return this.itemCount == 0
}

/**
 * Функция, которая возвращает первый элемент [ленивой страницы][LazyPagingItems].
 * Если страница [пустая][isEmpty], то вернётся null.
 */
fun <T : Any> LazyPagingItems<T>.firstOrNull(): T? {
    return if (isEmpty()) null else this[0]
}

/**
 * Функция, которая возвращает true, если страница загружается в первый раз.
 */
fun <T : Any> LazyPagingItems<T>.isFirstLoading(): Boolean {
    return this.loadState.refresh == LoadState.Loading
}

/**
 * Добавляет предметы в [ленивой странице][LazyPagingItems].
 *
 * @param items данные в ленивой странице
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