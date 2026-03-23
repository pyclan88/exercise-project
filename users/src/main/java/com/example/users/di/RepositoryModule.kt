package com.example.users.di

import com.example.users.domain.api.UsersAnalytics
import com.example.users.domain.api.UsersRepository
import com.example.users.data.analytics.UsersAnalyticsImpl
import com.example.users.data.repository.UsersRepositoryImpl
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