package ru.greenpix.moviecatalog.ui.view.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greenpix.moviecatalog.exception.AuthorizationException
import ru.greenpix.moviecatalog.repository.FavoriteRepository
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainFavorite
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainMovie
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainViewState
import ru.greenpix.moviecatalog.ui.view.screen.main.paging.MoviePagingSource
import java.net.SocketException
import java.net.UnknownHostException

class MainViewModel(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _viewState = mutableStateOf<MainViewState>(MainViewState.Loading)
    private val _favoritesState = mutableStateListOf<MainFavorite>()
    private val _deletedFavoritesState = mutableStateListOf<MainFavorite>()

    private val favoriteIdMap = mutableMapOf<String, MainFavorite>()

    val viewState: State<MainViewState>
        get() = _viewState
    val favoritesState: List<MainFavorite>
        get() = _favoritesState
    val deletedFavoritesState: List<MainFavorite>
        get() = _deletedFavoritesState

    val galleryFlow: Flow<PagingData<MainMovie>> = Pager(PagingConfig(pageSize = 6)) {
        MoviePagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    suspend fun load() {
        if (viewState.value !is MainViewState.Default) {
            _viewState.value = MainViewState.Loading
        }

        try {
            val favorites = favoriteRepository.getAllFavoriteMovies()
                .map {
                    MainFavorite(
                        movieId = it.id,
                        imageUrl = it.poster ?: ""
                    )
                }

            _favoritesState.clear()
            _deletedFavoritesState.clear()
            favoriteIdMap.clear()

            _favoritesState.addAll(favorites)
            favorites.forEach { favoriteIdMap[it.movieId] = it }

            _viewState.value = MainViewState.Default
        }
        catch (e: AuthorizationException) {
            _viewState.value = MainViewState.AuthorizationFailed
        }
        catch (e: Exception) {
            _viewState.value = when(e) {
                is HttpException -> MainViewState.HttpError
                is UnknownHostException, is SocketException -> MainViewState.NetworkError
                else -> {
                    e.printStackTrace()
                    MainViewState.UnknownError
                }
            }
        }
    }

    fun onDeleteFavorite(movieId: String) = viewModelScope.launch {
        try {
            favoriteIdMap[movieId]?.let { _deletedFavoritesState.add(it) }
            favoriteIdMap.remove(movieId)
            favoriteRepository.deleteFavoriteMovie(movieId)
        }
        catch (e: AuthorizationException) {
            _viewState.value = MainViewState.AuthorizationFailed
        }
        catch (e: Exception) {
            _viewState.value = when(e) {
                is HttpException -> MainViewState.HttpError
                is UnknownHostException, is SocketException -> MainViewState.NetworkError
                else -> {
                    e.printStackTrace()
                    MainViewState.UnknownError
                }
            }
        }
    }

    fun isFavoriteMovie(movieId: String): Boolean {
        return movieId in favoriteIdMap
    }
}