package com.example.myapplication.presentation.mappers

import com.example.myapplication.R
import com.example.myapplication.domain.models.User
import com.example.myapplication.presentation.models.UserVO
import com.example.myapplication.presentation.resources.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserToUserVOMapper @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun map(user: User): UserVO = UserVO(
        id = user.id,
        name = user.name,
        email = user.email,
        registrationDate = user.registrationDate,
        isActive = user.isActive,
        displayName = formatStatus(user)
    )

    // эта функция возвращает текст с именем и статусом пользователя
    private fun formatStatus(user: User): String {
        val status = if (user.isActive) {
            resourceProvider.getString(R.string.user_status_active)
        } else {
            resourceProvider.getString(R.string.user_status_inactive)
        }
        return "${user.name} ($status)"
    }
}