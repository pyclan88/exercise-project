package com.example.myapplication.coredomain.usecases

import com.example.myapplication.coredomain.api.UsersAnalytics
import com.example.myapplication.coredomain.api.UsersInteractor
import com.example.myapplication.coredomain.api.UsersRepository
import com.example.myapplication.coredomain.models.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersInteractorImpl @Inject constructor(
    private val repository: UsersRepository,
    private val analytics: UsersAnalytics,
) : UsersInteractor {

    // эта функция загружает пользователей
    override suspend fun loadUsers(): List<User> {
        return repository.getUsers()
    }

    // эта функция сохраняет пользователей
    override fun saveUser(id: Int) {
        repository.saveUser(id)
    }

    // эта функция отправляет логи
    override fun sendLogs(user: User) {
        analytics.sendLogs(user)
    }

    // эта функция проводит фильтрацию пользователей и оставляет только активных
    override fun filterOnlyActiveUsers(users: List<User>): List<User> {
        return users.filter { user -> user.isActive && validateUser(user) }
    }

    // эта функция вычисляет дату регистрации
    override fun calculateRegistrationDate(user: User): Int {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val regDate = dateFormat.parse(user.registrationDate)
        val diff = Date().time - (regDate?.time ?: 0)
        return (diff / (365L * 24 * 60 * 60 * 1000)).toInt()
    }

    // эта функция проводит проверку пользователя
    private fun validateUser(user: User): Boolean {
        return user.name.isNotEmpty() &&
                user.email.contains("@") &&
                user.id > 0
    }
}