package com.example.users.domain.api

import com.example.users.domain.models.User

interface UsersRepository {
    suspend fun getUsers(): List<User>
    fun saveUser(id: Int)
}