package com.example.myapplication.di

import com.example.myapplication.coredomain.api.UsersInteractor
import com.example.myapplication.coredomain.usecases.UsersInteractorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class InteractorModule {

    @Binds
    abstract fun bindUsersInteractor(
        impl: UsersInteractorImpl
    ): UsersInteractor

}