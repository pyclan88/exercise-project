package com.example.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.api.UsersInteractor
import com.example.myapplication.presentation.mappers.UserToUserVOMapper
import javax.inject.Inject

class UsersViewModelFactory @Inject constructor(
    private val usersInteractor: UsersInteractor,
    private val userToUserVOMapper: UserToUserVOMapper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == UsersViewModel::class.java) {
            "Unknown ViewModel class: ${modelClass.name}"
        }

        val viewModel = UsersViewModel(
            interactor = usersInteractor,
            userToUserVOMapper = userToUserVOMapper
        )

        return modelClass.cast(viewModel)
    }
}