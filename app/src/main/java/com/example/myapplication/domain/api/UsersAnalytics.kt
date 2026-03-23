package com.example.myapplication.domain.api

import com.example.myapplication.domain.models.User

interface UsersAnalytics {
    fun sendLogs(user: User)
}