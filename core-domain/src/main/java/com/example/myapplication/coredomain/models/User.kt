package com.example.myapplication.coredomain.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val registrationDate: String,
    val isActive: Boolean
)
