package com.example.users.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.users.presentation.mappers.UserToUserVOMapper
import com.example.users.presentation.mappers.UserVOToUserMapper
import com.example.users.presentation.models.UserVO
import com.example.users.presentation.states.UiEvent
import com.example.users.presentation.states.UiState
import com.example.users.presentation.states.UsersContent
import com.example.users.domain.api.UsersInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val interactor: UsersInteractor,
    private val userToUserVOMapper: UserToUserVOMapper
) : ViewModel() {

    private val _screenState: MutableStateFlow<UiState> =
        MutableStateFlow(
            UiState.Initial(
                data = UsersContent(
                    allUsers = emptyList(),
                    showOnlyActive = false
                )
            )
        )
    val screenState: StateFlow<UiState> = _screenState

    private val _events: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val events: SharedFlow<UiEvent> = _events

    // эта функция загружает пользователей
    fun loadUsers() {
        val currentContent = _screenState.value.data

        _screenState.value = UiState.Loading(
            data = currentContent
        )

        viewModelScope.launch {
            try {
                val loadedUsers = interactor.loadUsers().map { userToUserVOMapper.map(it) }

                _screenState.value = UiState.Content(
                    data = currentContent.copy(
                        allUsers = loadedUsers
                    )
                )
            } catch (e: Exception) {
                _screenState.value = UiState.Error(
                    message = "Error: ${e.message}",
                    data = currentContent
                )
            }
        }
    }

    // эта функция решает каких пользователей вернуть
    fun visibleUsers(content: UsersContent): List<UserVO> {
        return if (content.showOnlyActive) {
            getOnlyActiveUsers(content.allUsers)
        } else {
            content.allUsers
        }
    }

    // эта функция выполняет действие при клике на чекбокс "Показывать только активных пользователей"
    fun onOnlyActiveUsersCheckBoxClicked(value: Boolean) {
        val newContent = UsersContent(
            allUsers = _screenState.value.data.allUsers,
            showOnlyActive = value
        )

        when (_screenState.value) {
            is UiState.Initial -> _screenState.value = UiState.Initial(data = newContent)
            is UiState.Error -> _screenState.value = UiState.Error(
                message = (_screenState.value as UiState.Error).message,
                data = newContent
            )

            is UiState.Content -> _screenState.value = UiState.Content(data = newContent)
            else -> {}
        }
    }

    // эта функция выполняет действие при клике на карточку пользователя
    fun onCardClick(user: UserVO) {
        interactor.sendLogs(UserVOToUserMapper.map(user))
        interactor.saveUser(user.id)

        val age = interactor.calculateRegistrationDate(UserVOToUserMapper.map(user))
        viewModelScope.launch {
            _events.emit(UiEvent.ShowToast(age.toString()))
        }

        _screenState.value = UiState.Content(
            selectedUser = user,
            data = UsersContent(
                allUsers = _screenState.value.data.allUsers,
                showOnlyActive = _screenState.value.data.showOnlyActive,
            )
        )
    }

    // эта функция возвращает только активных пользователей
    private fun getOnlyActiveUsers(users: List<UserVO>): List<UserVO> {
        val domainUsers = users.map { UserVOToUserMapper.map(it) }
        val filteredUsers = interactor.filterOnlyActiveUsers(domainUsers)
        return filteredUsers.map { userToUserVOMapper.map(it) }
    }
}