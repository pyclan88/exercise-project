package com.example.myapplication.presentation.mappers

import com.example.myapplication.coredomain.models.User
import com.example.myapplication.presentation.models.UserVO

object UserVOToUserMapper {
    fun map(userVO: UserVO): User = User(
        id = userVO.id,
        name = userVO.name,
        email = userVO.email,
        registrationDate = userVO.registrationDate,
        isActive = userVO.isActive
    )  
}