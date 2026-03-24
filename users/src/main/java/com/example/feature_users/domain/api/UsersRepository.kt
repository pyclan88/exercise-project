package com.example.feature_users.domain.api

import com.example.feature_users.domain.models.User

interface UsersRepository {
    suspend fun getUsers(): List<User>
    fun saveUser(id: Int)
}