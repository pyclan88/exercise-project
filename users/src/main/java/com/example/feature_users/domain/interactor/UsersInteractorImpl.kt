package com.example.feature_users.domain.interactor

import com.example.feature_users.domain.api.UsersAnalytics
import com.example.feature_users.domain.api.UsersInteractor
import com.example.feature_users.domain.api.UsersRepository
import com.example.feature_users.domain.models.User
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
    override suspend fun loadAllUsers(): List<User> {
        return repository.getUsers()
    }

    // эта функция проводит фильтрацию пользователей и оставляет только активных
    override suspend fun loadOnlyActiveUsers(): List<User> {
        return loadAllUsers().filter { user -> user.isActive && validateUser(user) }
    }

    // эта функция сохраняет пользователей
    override fun saveUser(id: Int) {
        repository.saveUser(id)
    }

    // эта функция отправляет логи
    override fun sendLogs(user: User) {
        analytics.sendLogs(user)
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