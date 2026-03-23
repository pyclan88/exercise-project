package com.example.users.presentation.mappers

import com.example.users.domain.models.User
import com.example.users.presentation.models.UserVO

object UserVOToUserMapper {
    fun map(userVO: UserVO): User =
        User(
            id = userVO.id,
            name = userVO.name,
            email = userVO.email,
            registrationDate = userVO.registrationDate,
            isActive = userVO.isActive
        )
}