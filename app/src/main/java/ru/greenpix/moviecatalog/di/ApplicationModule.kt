package ru.greenpix.moviecatalog.di

import com.google.gson.Gson
import org.koin.dsl.module

val applicationModule = module {
    single { Gson() }
}