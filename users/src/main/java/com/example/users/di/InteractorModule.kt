package com.example.users.di

import com.example.users.domain.api.UsersInteractor
import com.example.users.domain.usecases.UsersInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindUsersInteractor(
        impl: UsersInteractorImpl
    ): UsersInteractor

}