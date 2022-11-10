package ru.greenpix.moviecatalog.mapper

import ru.greenpix.moviecatalog.domain.User
import ru.greenpix.moviecatalog.dto.UserShortDto

class UserMapper : Mapper<UserShortDto, User> {

    override fun map(source: UserShortDto): User = User(
        userId = source.userId,
        username = source.nickName,
        avatar = source.avatar
    )
}