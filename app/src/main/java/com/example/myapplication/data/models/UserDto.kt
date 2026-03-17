package com.example.myapplication.data.models

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val registrationDate: String,
    val isActive: Boolean
)
