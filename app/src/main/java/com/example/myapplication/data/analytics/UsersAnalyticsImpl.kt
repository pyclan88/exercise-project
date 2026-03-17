package com.example.myapplication.data.analytics

import com.example.myapplication.coredomain.api.UsersAnalytics
import com.example.myapplication.coredomain.models.User
import com.example.myapplication.data.mappers.UserToUserDtoMapper
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