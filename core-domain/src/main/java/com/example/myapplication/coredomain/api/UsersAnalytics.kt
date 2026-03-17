package com.example.myapplication.coredomain.api

import com.example.myapplication.coredomain.models.User

interface UsersAnalytics {
    fun sendLogs(user: User)
}