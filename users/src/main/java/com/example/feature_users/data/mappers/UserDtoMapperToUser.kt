package com.example.feature_users.data.mappers

import com.example.feature_users.domain.models.User
import com.example.feature_users.data.models.UserDto

object UserDtoMapperToUser {

    fun map(userDto: UserDto): User =
        User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
            registrationDate = userDto.registrationDate,
            isActive = userDto.isActive
        )
}
