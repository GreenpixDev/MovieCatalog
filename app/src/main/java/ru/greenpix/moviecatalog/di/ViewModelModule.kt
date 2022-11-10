package ru.greenpix.moviecatalog.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.greenpix.moviecatalog.ui.dialog.review.ReviewViewModel
import ru.greenpix.moviecatalog.ui.screen.main.MainViewModel
import ru.greenpix.moviecatalog.ui.screen.movie.MovieViewModel
import ru.greenpix.moviecatalog.ui.screen.profile.ProfileViewModel
import ru.greenpix.moviecatalog.ui.screen.signin.SignInViewModel
import ru.greenpix.moviecatalog.ui.screen.signup.SignUpViewModel

val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::ReviewViewModel)
}