package ru.greenpix.moviecatalog.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase
import ru.greenpix.moviecatalog.usecase.ValidationUseCase
import ru.greenpix.moviecatalog.usecase.impl.AuthorizationUseCaseImpl
import ru.greenpix.moviecatalog.usecase.impl.ValidationUseCaseImpl

val useCaseModule = module {
    singleOf(::AuthorizationUseCaseImpl) { bind<AuthorizationUseCase>() }
    singleOf(::ValidationUseCaseImpl) { bind<ValidationUseCase>() }
}