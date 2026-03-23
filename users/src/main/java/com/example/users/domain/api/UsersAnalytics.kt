package com.example.users.domain.api

import com.example.users.domain.models.User

interface UsersAnalytics {
    fun sendLogs(user: User)
}