package com.example.feature_users.presentation.states

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
}