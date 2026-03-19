package com.example.myapplication.di

import com.example.myapplication.presentation.resources.ResourceProvider
import com.example.myapplication.presentation.resources.ResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ResourcesModule {

    @Binds
    abstract fun bindResourcesProvider(
        impl: ResourceProviderImpl
    ): ResourceProvider
}