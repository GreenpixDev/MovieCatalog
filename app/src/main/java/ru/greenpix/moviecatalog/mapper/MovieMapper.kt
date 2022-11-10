package ru.greenpix.moviecatalog.mapper

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.dto.MovieElementDto

class MovieMapper : Mapper<MovieElementDto, Movie> {

    override fun map(source: MovieElementDto): Movie = Movie(
        id = source.id,
        name = source.name,
        poster = source.poster,
        year = source.year,
        country = source.country,
        genres = source.genres
            .mapNotNull { genre -> genre.name },
        rating = source.reviews
            .map { review -> review.rating }
            .average()
    )
}