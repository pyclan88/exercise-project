package com.example.users.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            USER_PREFS_KEY,
            MODE_PRIVATE
        )
    }

    companion object {
        const val USER_PREFS_KEY = "user_prefs"
    }
}