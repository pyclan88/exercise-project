package com.example.myapplication.presentation.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.viewModel.UsersViewModel
import com.example.myapplication.presentation.viewModel.UsersViewModelFactory

@Composable
fun MainScreenRoute(usersViewModelFactory: UsersViewModelFactory) {

    val context = LocalContext.current

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