package com.example.users.data.analytics

import com.example.users.domain.api.UsersAnalytics
import com.example.users.domain.models.User
import com.example.users.data.mappers.UserToUserDtoMapper
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersAnalyticsImpl @Inject constructor() : UsersAnalytics {
    // эта функция отправляет логи пользователя для аналитики
    override fun sendLogs(user: User) {
        val userDto = UserToUserDtoMapper.map(user)
        println("Analytics: User ${userDto.id} viewed at ${Date()}")
    }
}