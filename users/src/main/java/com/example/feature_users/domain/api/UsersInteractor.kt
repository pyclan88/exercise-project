package com.example.feature_users.domain.api

import com.example.feature_users.domain.models.User

interface UsersInteractor {
    suspend fun loadAllUsers(): List<User>
    suspend fun loadOnlyActiveUsers(): List<User>
    fun saveUser(id: Int)
    fun sendLogs(user: User)
    fun calculateRegistrationDate(user: User): Int
}