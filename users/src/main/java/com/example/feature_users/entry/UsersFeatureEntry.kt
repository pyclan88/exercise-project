package com.example.feature_users.entry

import android.content.Context
import com.example.feature_users_api.UsersFeatureApi

object UsersFeatureEntry {
    fun create(context: Context): UsersFeatureApi {
        return UsersFeatureApiImpl(context)
    }
}