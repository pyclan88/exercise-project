package com.example.myapplication.domain.api

import com.example.myapplication.domain.models.User

interface UsersRepository {
    suspend fun getUsers(): List<User>
    fun saveUser(id: Int)
}