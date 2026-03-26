package com.example.feature_users.data.mappers

import com.example.feature_users.domain.models.User
import com.example.feature_users.data.models.UserDto

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