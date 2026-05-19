package com.example.feature_users.entry

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.feature_users.presentation.ui.UsersScreenRoute
import com.example.feature_users_api.UsersFeatureApi

class UsersFeatureApiImpl(private val context: Context) : UsersFeatureApi {
    @Composable
    override fun Content() {
        UsersScreenRoute(context)
    }
}