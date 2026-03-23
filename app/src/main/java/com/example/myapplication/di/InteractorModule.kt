package com.example.myapplication.di

import com.example.myapplication.domain.api.UsersInteractor
import com.example.myapplication.domain.usecases.UsersInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindUsersInteractor(
        impl: UsersInteractorImpl
    ): UsersInteractor

}