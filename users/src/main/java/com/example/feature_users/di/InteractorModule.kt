package com.example.feature_users.di

import com.example.feature_users.domain.api.UsersInteractor
import com.example.feature_users.domain.interactor.UsersInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindUsersInteractor(
        impl: UsersInteractorImpl
    ): UsersInteractor

}