package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.presentation.ui.MainScreenRoute
import com.example.myapplication.presentation.viewModel.UsersViewModelFactory
import com.example.myapplication.theme.AppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreenRoute(usersViewModelFactory)
            }
        }
    }
}