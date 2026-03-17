package com.example.myapplication.data.mappers

import com.example.myapplication.coredomain.models.User
import com.example.myapplication.data.models.UserDto

object UserDtoMapperToUser {

    fun map(userDto: UserDto): User = User(
        id = userDto.id,
        name = userDto.name,
        email = userDto.email,
        registrationDate = userDto.registrationDate,
        isActive = userDto.isActive
    )
}
