package ru.greenpix.moviecatalog.ui.view.screen.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.ui.view.screen.main.model.MainMovie

class MoviePagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, MainMovie>() {

    override fun getRefreshKey(state: PagingState<Int, MainMovie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainMovie> {
        val page = movieRepository.getPage(params.key ?: 1)

        return LoadResult.Page(
            data = page.movies
                .map { movie ->
                    MainMovie(
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
                            .takeIf { !it.isNaN() }
                            ?: 0f
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
}