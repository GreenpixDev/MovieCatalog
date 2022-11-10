package ru.greenpix.moviecatalog.ui.screen.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.screen.main.mapper.MainMovieMapper
import ru.greenpix.moviecatalog.ui.screen.main.model.MainMovieModel

class MoviePagingSource(
    private val movieRepository: MovieRepository,
    private val mainMovieMapper: MainMovieMapper
) : PagingSource<Int, MainMovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MainMovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainMovieModel> {
        return try {
            val page = movieRepository.getPage(params.key ?: 1)

            LoadResult.Page(
                data = page.content.map { mainMovieMapper.map(it) },
                prevKey = if (page.isFirst) null else page.pageNumber - 1,
                nextKey = if (page.isLast) null else page.pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}