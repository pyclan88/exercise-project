package com.example.feature_users.domain.api

import com.example.feature_users.domain.models.User

interface UsersInteractor {
    suspend fun loadUsers(): List<User>
    fun saveUser(id: Int)
    fun sendLogs(user: User)
    fun filterOnlyActiveUsers(users: List<User>): List<User>
    fun calculateRegistrationDate(user: User): Int
}