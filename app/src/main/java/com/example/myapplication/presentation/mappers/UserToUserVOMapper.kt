package com.example.myapplication.presentation.mappers

import com.example.myapplication.coredomain.models.User
import com.example.myapplication.presentation.models.UserVO

object UserToUserVOMapper {

    fun map(user: User): UserVO = UserVO(
        id = user.id,
        name = user.name,
        email = user.email,
        registrationDate = user.registrationDate,
        isActive = user.isActive
    )
}