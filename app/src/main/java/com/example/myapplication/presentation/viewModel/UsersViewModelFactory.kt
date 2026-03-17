package com.example.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.coredomain.api.UsersInteractor
import javax.inject.Inject

class UsersViewModelFactory @Inject constructor(
    private val usersInteractor: UsersInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == UsersViewModel::class.java) {
            "Unknown ViewModel class: ${modelClass.name}"
        }

        return UsersViewModel(usersInteractor) as T
    }
}