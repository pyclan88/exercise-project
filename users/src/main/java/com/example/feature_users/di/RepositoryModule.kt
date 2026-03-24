package com.example.feature_users.di

import com.example.feature_users.domain.api.UsersAnalytics
import com.example.feature_users.domain.api.UsersRepository
import com.example.feature_users.data.analytics.UsersAnalyticsImpl
import com.example.feature_users.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    abstract fun bindUsersAnalytics(
        impl: UsersAnalyticsImpl
    ): UsersAnalytics
}