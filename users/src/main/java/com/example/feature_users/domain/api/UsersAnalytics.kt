package com.example.feature_users.domain.api

import com.example.feature_users.domain.models.User

interface UsersAnalytics {
    fun sendLogs(user: User)
}