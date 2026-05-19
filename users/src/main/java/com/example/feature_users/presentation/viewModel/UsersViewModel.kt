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
import com.example.feature_users.presentation.states.ExerciseState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.cancellation.CancellationException

class UsersViewModel(
    private val interactor: UsersInteractor,
    private val userToUserVOMapper: UserToUserVOMapper
) : ViewModel() {

    init {
        simultaneously()
        sequentially()
    }

    private fun simultaneously() {
        viewModelScope.launch {
            val api1 = async { api1() }
            val api2 = async { api2() }
            val api3 = async { api3() }

            val result = awaitAll(
                api1,
                api2,
                api3
            ).sum()

            println("Simultaneously result: $result")
        }
    }

    fun simultaneouslyContinueOnError() {
        viewModelScope.launch {
            val result = supervisorScope {
                val api1 = async { runApiSafely { api1() } }
                val api2 = async { runApiSafely { api2WithError() } }
                val api3 = async { runApiSafely { api3() } }

                awaitAll(api1, api2, api3).sum()
            }

            _exerciseState.value = _exerciseState.value.copy(simCon = result.toString())
        }
    }

    fun simultaneouslyCancelOnError() {
        viewModelScope.launch {
            try {
                val result = coroutineScope {
                    val api1 = async { api1() }
                    val api2 = async { api2WithError() }
                    val api3 = async { api3() }

                    awaitAll(api1, api2, api3).sum()
                }

                _exerciseState.value = _exerciseState.value.copy(simCan = result.toString())
            } catch (e: Exception) {
                _exerciseState.value = _exerciseState.value.copy(simCan = e.message)
            }
        }
    }


    private fun sequentially() {
        viewModelScope.launch {
            val result = api1() + api2() + api3()

            println("Sequentially result: $result")
        }
    }

    fun sequentiallyContinueOnError() {
        viewModelScope.launch {
            val result =
                runApiSafely { api1() } +
                        runApiSafely { api2WithError() } +
                        runApiSafely { api3() }

            _exerciseState.value = _exerciseState.value.copy(seqCon = result.toString())
        }
    }

    fun sequentiallyCancelOnError() {
        viewModelScope.launch {
            try {
                val result = api1() + api2WithError() + api3()

                _exerciseState.value = _exerciseState.value.copy(seqCan = result.toString())
            } catch (e: Exception) {
                _exerciseState.value = _exerciseState.value.copy(seqCan = e.message)
            }
        }
    }

    private suspend fun runApiSafely(apiCall: suspend () -> Int): Int {
        return try {
            apiCall()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            0
        }
    }

    private suspend fun api1(): Int {
        delay(1000)
        return 1
    }

    private suspend fun api2(): Int {
        delay(1500)
        return 2
    }

    private suspend fun api2WithError(): Int {
        delay(1500)
        throw RuntimeException("Api 2\nfailed")
    }

    private suspend fun api3(): Int {
        delay(2000)
        return 3
    }

    private val _exerciseState: MutableStateFlow<ExerciseState> = MutableStateFlow(ExerciseState())
    val exerciseState: StateFlow<ExerciseState> = _exerciseState





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