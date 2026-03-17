package com.example.myapplication.presentation.states

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
}