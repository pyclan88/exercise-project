package com.example.feature_users.presentation.states

import com.example.feature_users.presentation.models.UserVO

sealed class UiState(open val data: UsersContent) {
    data class Initial(
        override val data: UsersContent
    ) : UiState(data)

    data class Loading(
        override val data: UsersContent
    ) : UiState(data)

    data class Error(
        val message: String,
        override val data: UsersContent
    ) : UiState(data)

    data class Content(
        val selectedUser: UserVO? = null,
        override val data: UsersContent
    ) : UiState(data)
}

data class UsersContent(
    val users: List<UserVO>,
    val showOnlyActive: Boolean
)
