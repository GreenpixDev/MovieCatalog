package ru.greenpix.moviecatalog.di

import com.google.gson.Gson
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
import ru.greenpix.moviecatalog.mapper.*
import ru.greenpix.moviecatalog.repository.*
import ru.greenpix.moviecatalog.repository.impl.*
import ru.greenpix.moviecatalog.retrofit.*
import ru.greenpix.moviecatalog.ui.dialog.review.ReviewViewModel
import ru.greenpix.moviecatalog.ui.screen.main.MainViewModel
import ru.greenpix.moviecatalog.ui.screen.main.mapper.MainFavoriteMapper
import ru.greenpix.moviecatalog.ui.screen.main.mapper.MainMovieMapper
import ru.greenpix.moviecatalog.ui.screen.movie.MovieViewModel
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieReviewModel
import ru.greenpix.moviecatalog.ui.screen.profile.ProfileViewModel
import ru.greenpix.moviecatalog.ui.screen.signin.SignInViewModel
import ru.greenpix.moviecatalog.ui.screen.signup.SignUpViewModel
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase
import ru.greenpix.moviecatalog.usecase.ValidationUseCase
import ru.greenpix.moviecatalog.usecase.impl.AuthorizeUseCaseImpl
import ru.greenpix.moviecatalog.usecase.impl.ValidationUseCaseImpl

val appModule = module {
    single { Gson() }
    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.server_url))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    retrofitOf<AuthenticationApi>()
    retrofitOf<MovieApi>()
    retrofitOf<FavoriteApi>()
    retrofitOf<UserApi>()
    retrofitOf<ReviewApi>()

    singleOf(::JwtRepositoryImpl) { bind<JwtRepository>() }
    singleOf(::AuthenticationRepositoryImpl) { bind<AuthenticationRepository>() }
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    singleOf(::FavoriteRepositoryImpl) { bind<FavoriteRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::ReviewRepositoryImpl) { bind<ReviewRepository>() }

    singleOf(::AuthorizeUseCaseImpl) { bind<AuthorizationUseCase>() }
    singleOf(::ValidationUseCaseImpl) { bind<ValidationUseCase>() }

    singleOf(::UserMapper)
    singleOf(::ReviewMapper)
    singleOf(::MovieDetailsMapper)
    singleOf(::MovieMapper)
    singleOf(::MoviesPageMapper)
    singleOf(::MovieReviewModel)
    singleOf(::MainFavoriteMapper)
    singleOf(::MainMovieMapper)

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