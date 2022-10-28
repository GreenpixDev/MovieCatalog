package ru.greenpix.moviecatalog.ui.view.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainFavorite
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainMovie
import ru.greenpix.moviecatalog.ui.view.screen.main.paging.MoviePagingSource
import ru.greenpix.moviecatalog.ui.view.shared.model.ViewState

class MainViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _loadState = mutableStateOf(ViewState.UNLOADED)
    private val _favoritesState = mutableStateMapOf<String, MainFavorite>()

    val loadState: State<ViewState>
        get() = _loadState
    val favoritesState: Map<String, MainFavorite>
        get() = _favoritesState

    val galleryFlow: Flow<PagingData<MainMovie>> = Pager(PagingConfig(pageSize = 6)) {
        MoviePagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun load() {
        try {
            if (loadState.value == ViewState.LOADED) {
                return
            }
            _loadState.value = ViewState.LOADING
            _favoritesState.clear()

            _favoritesState.putAll(
                favoriteRepository.getAllFavoriteMovies()
                    .associateBy { it.id }
                    .mapValues { MainFavorite(imageUrl = it.value.poster ?: "") }
            )
            _loadState.value = ViewState.LOADED
        }
        catch (e: AuthorizationException) {
            // TODO перенаправляем на экран авторизации
        }
        catch (e: Exception) {
            e.printStackTrace()
            // TODO надо бы сделать обработку ошибок
        }
    }

    fun onDeleteFavorite(movieId: String) {
        // TODO обращаемся к репозиторию
        _favoritesState.remove(movieId)
    }

}