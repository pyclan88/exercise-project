package com.example.myapplication.di

import com.example.myapplication.domain.api.UsersAnalytics
import com.example.myapplication.domain.api.UsersRepository
import com.example.myapplication.data.analytics.UsersAnalyticsImpl
import com.example.myapplication.data.repository.UsersRepositoryImpl
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