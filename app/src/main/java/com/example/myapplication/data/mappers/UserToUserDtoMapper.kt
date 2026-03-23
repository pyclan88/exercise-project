package com.example.myapplication.data.mappers

import com.example.myapplication.domain.models.User
import com.example.myapplication.data.models.UserDto

object UserToUserDtoMapper {
    fun map(user: User): UserDto = UserDto(
        id = user.id,
        name = user.name,
        email = user.email,
        registrationDate = user.registrationDate,
        isActive = user.isActive
    )
}