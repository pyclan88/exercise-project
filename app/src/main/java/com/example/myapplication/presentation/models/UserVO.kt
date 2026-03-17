package com.example.myapplication.presentation.models

data class UserVO(
    val id: Int,
    val name: String,
    val email: String,
    val registrationDate: String,
    val isActive: Boolean
)
