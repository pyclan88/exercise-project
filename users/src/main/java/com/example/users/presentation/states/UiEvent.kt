package com.example.users.presentation.states

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
}