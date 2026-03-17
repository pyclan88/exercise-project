package com.example.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.coredomain.api.UsersInteractor
import com.example.myapplication.presentation.mappers.UserToUserVOMapper
import com.example.myapplication.presentation.mappers.UserVOToUserMapper
import com.example.myapplication.presentation.models.UserVO
import com.example.myapplication.presentation.states.UiEvent
import com.example.myapplication.presentation.states.UiState
import com.example.myapplication.presentation.states.UsersContent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UsersViewModel(
    private val interactor: UsersInteractor
) : ViewModel() {

    private val _screenState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial())
    val screenState: StateFlow<UiState> = _screenState

    private val _events: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val events: SharedFlow<UiEvent> = _events

    private var loadedUsers: List<UserVO> = listOf()
    private var showOnlyActive: Boolean = false

    // эта функция загружает пользователей
    fun loadUsers() {
        _screenState.value = UiState.Loading(
            data = UsersContent(
                users = loadedUsers,
                showOnlyActive = showOnlyActive
            )
        )
        viewModelScope.launch {
            try {
                loadedUsers = interactor.loadUsers().map { UserToUserVOMapper.map(it) }

                val displayUsers = if (showOnlyActive) filterActiveUsers() else loadedUsers

                _screenState.value = UiState.Content(
                    data = UsersContent(
                        users = displayUsers,
                        showOnlyActive = showOnlyActive
                    )
                )
            } catch (e: Exception) {
                _screenState.value = UiState.Error(
                    message = "Error: ${e.message}",
                    data = UsersContent(
                        users = loadedUsers,
                        showOnlyActive = showOnlyActive
                    )
                )
            }
        }
    }

    // эта функция выполняет действие при клике на чекбокс "Показывать только активных пользователей"
    fun onOnlyActiveUsersCheckBoxClicked(value: Boolean) {
        _screenState.value.data?.let {
            val newUsers = if (value) {
                filterActiveUsers()
            } else {
                loadedUsers
            }

            val newUsersContent = UsersContent(
                users = newUsers,
                showOnlyActive = value
            )

            _screenState.value = when (_screenState.value) {
                is UiState.Initial -> UiState.Initial(data = newUsersContent)
                is UiState.Loading -> UiState.Loading(data = newUsersContent)
                is UiState.Error -> UiState.Error(
                    message = (_screenState.value as UiState.Error).message,
                    data = newUsersContent
                )

                is UiState.Content -> UiState.Content(data = newUsersContent)
            }

            showOnlyActive = value
        }
    }

    // эта функция фильтрует активных пользователей
    private fun filterActiveUsers(): List<UserVO> {
        val domainUsers = loadedUsers.map { UserVOToUserMapper.map(it) }
        val filteredUsers = interactor.filterOnlyActiveUsers(domainUsers)
        return filteredUsers.map { UserToUserVOMapper.map(it) }
    }

    // эта функция выполняет действие при клике на карточку пользователя
    fun onCardClick(user: UserVO) {
        interactor.sendLogs(UserVOToUserMapper.map(user))
        interactor.saveUser(user.id)

        val age = calculateRegistrationDate(user)
        viewModelScope.launch {
            _events.emit(UiEvent.ShowToast(age.toString()))
        }

        _screenState.value = UiState.Content(
            selectedUser = user,
            data = UsersContent(
                users = loadedUsers,
                showOnlyActive = showOnlyActive,
            )
        )
    }

    // эта функция вычисляет дату регистрации
    private fun calculateRegistrationDate(user: UserVO): Int {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val regDate = dateFormat.parse(user.registrationDate)
        val diff = Date().time - (regDate?.time ?: 0)
        return (diff / (365L * 24 * 60 * 60 * 1000)).toInt()
    }
}