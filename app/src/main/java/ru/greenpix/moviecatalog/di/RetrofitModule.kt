package ru.greenpix.moviecatalog.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.greenpix.moviecatalog.R
import ru.greenpix.moviecatalog.retrofit.*

val retrofitModule = module {
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
}

inline fun <reified R : Any> Module.retrofitOf(): KoinDefinition<R> {
    return single { get<Retrofit>().create(R::class.java) }
}