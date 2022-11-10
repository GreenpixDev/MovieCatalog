package ru.greenpix.moviecatalog.ui.screen.main.mapper

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.mapper.Mapper
import ru.greenpix.moviecatalog.ui.screen.main.model.MainMovieModel

class MainMovieMapper : Mapper<Movie, MainMovieModel> {

    override fun map(source: Movie): MainMovieModel = MainMovieModel(
        id = source.id,
        name = checkNotNull(source.name) { "name of movie (id: ${source.id}) cannot be null" },
        imageUrl = source.posterUrl ?: "",
        country = checkNotNull(source.country) { "country of movie (id: ${source.id}) cannot be null" },
        year = source.year,
        genres = source.genres.joinToString(),
        rating = source.rating.toFloat()
    )
}