package com.example.myapplication.coredomain.api

import com.example.myapplication.coredomain.models.User

interface UsersRepository {
    suspend fun getUsers(): List<User>
    fun saveUser(id: Int)
}