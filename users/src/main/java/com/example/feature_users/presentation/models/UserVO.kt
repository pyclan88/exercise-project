package com.example.feature_users.presentation.models

data class UserVO(
    val id: Int,
    val name: String,
    val email: String,
    val registrationDate: String,
    val isActive: Boolean,
    val displayName: String
)