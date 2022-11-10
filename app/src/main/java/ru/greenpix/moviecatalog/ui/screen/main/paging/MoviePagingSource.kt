package ru.greenpix.moviecatalog.ui.screen.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.screen.main.model.MainMovieModel

class MoviePagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, MainMovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MainMovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainMovieModel> {
        try {
            val page = movieRepository.getPage(params.key ?: 1)

            return LoadResult.Page(
                data = page.movies
                    .map { movie ->
                        MainMovieModel(
                            id = movie.id,
                            name = movie.name ?: "", // TODO норм обработать null?
                            imageUrl = movie.poster ?: "",
                            country = movie.country ?: "", // TODO норм обработать null?
                            year = movie.year,
                            genres = movie.genres
                                .map { it.name }
                                .joinToString(),
                            rating = movie.reviews
                                .map { it.rating }
                                .average()
                                .toFloat()
                        )
                    },
                prevKey = if (page.pageInfo.currentPage == 1) {
                    null
                } else {
                    page.pageInfo.currentPage - 1
                },
                nextKey = if (page.pageInfo.currentPage == page.pageInfo.pageCount) {
                    null
                } else {
                    page.pageInfo.currentPage + 1
                }
            )
        }
        catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}