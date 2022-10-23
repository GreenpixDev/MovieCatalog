package ru.greenpix.moviecatalog.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.repository.impl.JwtRepositoryImpl
import ru.greenpix.moviecatalog.ui.view.dialog.review.ReviewViewModel
import ru.greenpix.moviecatalog.ui.view.screen.auth.signin.SignInViewModel
import ru.greenpix.moviecatalog.ui.view.screen.auth.signup.SignUpViewModel
import ru.greenpix.moviecatalog.ui.view.screen.home.main.MainViewModel
import ru.greenpix.moviecatalog.ui.view.screen.home.profile.ProfileViewModel
import ru.greenpix.moviecatalog.ui.view.screen.movie.MovieViewModel

val appModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::ReviewViewModel)

    singleOf(::JwtRepositoryImpl) { bind<JwtRepository>() }
}