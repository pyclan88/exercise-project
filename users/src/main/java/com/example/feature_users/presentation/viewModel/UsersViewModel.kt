package com.example.feature_users.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_users.presentation.mappers.UserToUserVOMapper
import com.example.feature_users.presentation.mappers.UserVOToUserMapper
import com.example.feature_users.presentation.models.UserVO
import com.example.feature_users.presentation.states.UiEvent
import com.example.feature_users.presentation.states.UiState
import com.example.feature_users.presentation.states.UsersContent
import com.example.feature_users.domain.api.UsersInteractor
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
                    users = emptyList(),
                    showOnlyActive = false
                )
            )
        )
    val screenState: StateFlow<UiState> = _screenState

    private val _events: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val events: SharedFlow<UiEvent> = _events

    // эта функция решает каких пользователей загрузить
    fun loadUsers(showOnlyActive: Boolean) {
        val currentContent = _screenState.value.data.copy(showOnlyActive = showOnlyActive)

        _screenState.value = UiState.Loading(
            data = currentContent
        )

        viewModelScope.launch {
            try {
                val loadedUsers = if (showOnlyActive) {
                    interactor.loadOnlyActiveUsers().map { userToUserVOMapper.map(it) }
                } else {
                    interactor.loadAllUsers().map { userToUserVOMapper.map(it) }
                }

                _screenState.value = UiState.Content(
                    data = currentContent.copy(
                        users = loadedUsers
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

    // эта функция выполняет действие при клике на чекбокс "Показывать только активных пользователей"
    fun onOnlyActiveUsersCheckBoxClicked(showOnlyActive: Boolean) {
        val newContent = UsersContent(
            users = _screenState.value.data.users,
            showOnlyActive = showOnlyActive
        )

        when (_screenState.value) {
            is UiState.Initial -> _screenState.value = UiState.Initial(data = newContent)
            is UiState.Loading -> _screenState.value = UiState.Loading(data = newContent)
            else -> {
                loadUsers(showOnlyActive)
            }
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
                users = _screenState.value.data.users,
                showOnlyActive = _screenState.value.data.showOnlyActive,
            )
        )
    }
}