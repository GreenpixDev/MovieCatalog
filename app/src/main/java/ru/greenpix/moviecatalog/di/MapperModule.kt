package ru.greenpix.moviecatalog.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.greenpix.moviecatalog.mapper.*
import ru.greenpix.moviecatalog.ui.screen.main.mapper.MainFavoriteMapper
import ru.greenpix.moviecatalog.ui.screen.main.mapper.MainMovieMapper
import ru.greenpix.moviecatalog.ui.screen.movie.mapper.MovieReviewMapper

val mapperModule = module {
    singleOf(::UserMapper)
    singleOf(::ReviewMapper)
    singleOf(::MovieDetailsMapper)
    singleOf(::MovieMapper)
    singleOf(::MoviesPageMapper)

    singleOf(::MovieReviewMapper)
    singleOf(::MainFavoriteMapper)
    singleOf(::MainMovieMapper)
}