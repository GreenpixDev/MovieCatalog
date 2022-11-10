package ru.greenpix.moviecatalog.ui.screen.main.mapper

import ru.greenpix.moviecatalog.domain.Movie
import ru.greenpix.moviecatalog.mapper.Mapper
import ru.greenpix.moviecatalog.ui.screen.main.model.MainFavoriteModel

class MainFavoriteMapper : Mapper<Movie, MainFavoriteModel> {

    override fun map(source: Movie): MainFavoriteModel = MainFavoriteModel(
        movieId = source.id,
        imageUrl = source.poster ?: ""
    )
}