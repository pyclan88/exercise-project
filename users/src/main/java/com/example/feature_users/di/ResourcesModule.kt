package com.example.feature_users.di

import com.example.feature_users.presentation.resources.ResourceProvider
import com.example.feature_users.presentation.resources.ResourceProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ResourcesModule {

    @Binds
    abstract fun bindResourcesProvider(
        impl: ResourceProviderImpl
    ): ResourceProvider
}