package com.example.users.data.mappers

import com.example.users.domain.models.User
import com.example.users.data.models.UserDto

object UserToUserDtoMapper {
    fun map(user: User): UserDto =
        UserDto(
            id = user.id,
            name = user.name,
            email = user.email,
            registrationDate = user.registrationDate,
            isActive = user.isActive
        )
}