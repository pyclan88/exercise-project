package com.example.myapplication.coredomain.api

import com.example.myapplication.coredomain.models.User

interface UsersInteractor {
    suspend fun loadUsers(): List<User>
    fun saveUser(id: Int)
    fun sendLogs(user: User)
    fun filterOnlyActiveUsers(users: List<User>): List<User>
}