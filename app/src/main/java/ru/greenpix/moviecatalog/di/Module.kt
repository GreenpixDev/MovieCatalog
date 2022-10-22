package ru.greenpix.moviecatalog.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
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
}