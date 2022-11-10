package ru.greenpix.moviecatalog.mapper

import ru.greenpix.moviecatalog.domain.MovieDetails
import ru.greenpix.moviecatalog.dto.MovieDetailsDto

class MovieDetailsMapper(
    private val reviewMapper: ReviewMapper
) : Mapper<MovieDetailsDto, MovieDetails> {

    override fun map(source: MovieDetailsDto): MovieDetails = MovieDetails(
        id = source.id,
        name = source.name,
        poster = source.poster,
        year = source.year,
        country = source.country,
        genres = source.genres.mapNotNull { it.name },
        reviews = source.reviews.map { reviewMapper.map(it) },
        time = source.time,
        tagline = source.tagline,
        description = source.description,
        director = source.director,
        budget = source.budget,
        fees = source.fees,
        ageLimit = source.ageLimit
    )
}