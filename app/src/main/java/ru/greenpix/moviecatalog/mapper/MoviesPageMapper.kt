package ru.greenpix.moviecatalog.mapper

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.domain.Page
import ru.greenpix.moviecatalog.dto.MoviesPagedListDto

class MoviesPageMapper(
    private val movieMapper: MovieMapper
) : Mapper<MoviesPagedListDto, Page<Movie>> {

    override fun map(source: MoviesPagedListDto): Page<Movie> = Page(
        pageSize = source.pageInfo.pageSize,
        totalPageCount = source.pageInfo.pageCount,
        pageNumber = source.pageInfo.currentPage,
        content = source.movies.map { movieMapper.map(it) }
    )
}