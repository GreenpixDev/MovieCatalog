package ru.greenpix.moviecatalog.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.greenpix.moviecatalog.ui.view.screen.auth.signin.SignInViewModel

val appModule = module {
    viewModelOf(::SignInViewModel)
}