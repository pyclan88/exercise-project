package com.example.feature_users.di

import android.content.Context
import com.example.feature_users.presentation.viewModel.UsersViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ContextModule::class,
        ResourcesModule::class
    ]
)
interface UsersComponent {

    fun usersViewModelFactory(): UsersViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context
        ): UsersComponent
    }
}