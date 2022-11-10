package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.Gender
import ru.greenpix.moviecatalog.domain.Profile
import ru.greenpix.moviecatalog.dto.ProfileDto
import ru.greenpix.moviecatalog.repository.UserRepository
import ru.greenpix.moviecatalog.repository.util.ServerDateTime
import ru.greenpix.moviecatalog.retrofit.UserApi
import ru.greenpix.moviecatalog.usecase.AuthorizationUseCase
import java.time.LocalDateTime

class UserRepositoryImpl(
    private val authorizationUseCase: AuthorizationUseCase,
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getProfile(): Profile = authorizationUseCase.withAuthorization {
        userApi.getProfile(it).let { dto ->
            Profile(
                id = dto.id,
                username = dto.username,
                email = dto.email,
                avatarLink = dto.avatarLink,
                name = dto.name,
                birthday = LocalDateTime.parse(dto.birthday).toLocalDate(),
                gender = Gender.values()[dto.gender + 1]
            )
        }
    }

    override suspend fun updateProfile(profile: Profile) = authorizationUseCase.withAuthorization {
        userApi.putProfile(it, ProfileDto(
            id = profile.id,
            username = profile.username,
            email = profile.email,
            avatarLink = profile.avatarLink,
            name = profile.name,
            birthday = profile.birthday.atStartOfDay().format(ServerDateTime.formatter),
            gender = profile.gender.ordinal - 1
        ))
    }
}