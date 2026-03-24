package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.presentation.ui.MainActivity
import com.example.feature_users.di.ApplicationContext
import com.example.feature_users.di.ContextModule
import com.example.feature_users.di.InteractorModule
import com.example.feature_users.di.RepositoryModule
import com.example.feature_users.di.ResourcesModule
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
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @ApplicationContext context: Context
        ): AppComponent
    }
}