package com.example.myapplication.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.feature_users.entry.UsersFeatureEntry
import com.example.myapplication.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usersFeatureApi = UsersFeatureEntry.create(applicationContext)

        setContent {
            AppTheme {
                usersFeatureApi.Content()
            }
        }
    }
}