package com.example.myapplication

import android.content.Context
import com.example.users.di.ApplicationContext
import com.example.users.di.ContextModule
import com.example.users.di.InteractorModule
import com.example.users.di.RepositoryModule
import com.example.users.di.ResourcesModule
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