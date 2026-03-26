package com.example.feature_users.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature_users.di.DaggerUsersComponent
import com.example.feature_users.presentation.viewModel.UsersViewModel

@Composable
fun UsersScreenRoute(context: Context) {
    val usersComponent = remember(context) {
        DaggerUsersComponent.factory().create(context)
    }

    val usersViewModelFactory = remember(usersComponent) {
        usersComponent.usersViewModelFactory()
    }

    val viewModel: UsersViewModel = viewModel(
        factory = usersViewModelFactory
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            Toast.makeText(
                context,
                "User age: $event years",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    MainScreen(viewModel = viewModel)
}