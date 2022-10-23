package ru.greenpix.moviecatalog.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.repository.AuthenticateRepository
import ru.greenpix.moviecatalog.repository.JwtRepository
import ru.greenpix.moviecatalog.repository.impl.AuthenticateRepositoryImpl
import ru.greenpix.moviecatalog.repository.impl.JwtRepositoryImpl
import ru.greenpix.moviecatalog.retrofit.AuthenticateApi
import ru.greenpix.moviecatalog.ui.view.dialog.review.ReviewViewModel
import ru.greenpix.moviecatalog.ui.view.screen.auth.signin.SignInViewModel
import ru.greenpix.moviecatalog.ui.view.screen.auth.signup.SignUpViewModel
import ru.greenpix.moviecatalog.ui.view.screen.home.main.MainViewModel
import ru.greenpix.moviecatalog.ui.view.screen.home.profile.ProfileViewModel
import ru.greenpix.moviecatalog.ui.view.screen.movie.MovieViewModel

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.server_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    retrofitOf<AuthenticateApi>()

    singleOf(::JwtRepositoryImpl) { bind<JwtRepository>() }
    singleOf(::AuthenticateRepositoryImpl) { bind<AuthenticateRepository>() }

    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::ReviewViewModel)
}

inline fun <reified R : Any> Module.retrofitOf(): KoinDefinition<R> {
    return single { get<Retrofit>().create(R::class.java) }
}