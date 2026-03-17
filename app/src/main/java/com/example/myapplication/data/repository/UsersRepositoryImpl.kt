package com.example.myapplication.data.repository

import android.content.SharedPreferences
import com.example.myapplication.data.models.UserDto
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.content.edit
import com.example.myapplication.coredomain.api.UsersRepository
import com.example.myapplication.coredomain.models.User
import com.example.myapplication.data.mappers.UserDtoMapperToUser
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.forEach

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UsersRepository {

    private var cachedUsers: List<UserDto>? = null
    private var lastFetchTime: Long = 0

    // эта функция получает загруженных пользователей из сети
    override suspend fun getUsers(): List<User> {
        val currentTime = System.currentTimeMillis()
        if (cachedUsers != null && (currentTime - lastFetchTime) < 300000) {
            return cachedUsers!!.map { UserDtoMapperToUser.map(it) }
        }
        val users = loadUsers()
        storeToCache(users)
        cachedUsers = users
        lastFetchTime = currentTime
        return users.map { UserDtoMapperToUser.map(it) }
    }

    // эта функция сохраняет пользователя в БД
    override fun saveUser(id: Int) {
        sharedPreferences.edit {
            putInt(LAST_VIEWED_ID_KEY, id)
        }
    }

    // эта функция загружает пользователей из сети
    private suspend fun loadUsers(): List<UserDto> {
        delay(2000)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return (1..10).map { index ->
            UserDto(
                id = index,
                name = "User $index",
                email = "user$index@example.com",
                registrationDate = dateFormat.format(Date(System.currentTimeMillis() - index * 86400000L)),
                isActive = index % 2 == 0
            )
        }
    }

    // эта функция сохраняет пользователей в кеш
    private fun storeToCache(users: List<UserDto>) {
        users.forEach { user ->
            println("DB: Saving user ${user.id}")
        }
    }

    companion object {
        const val LAST_VIEWED_ID_KEY = "last_viewed_id"
    }
}