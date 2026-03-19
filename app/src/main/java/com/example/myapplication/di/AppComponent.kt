package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.presentation.ui.MainActivity
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