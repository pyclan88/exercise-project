package com.example.myapplication

import android.content.Context
import com.example.myapplication.di.ApplicationContext
import com.example.myapplication.di.ContextModule
import com.example.myapplication.di.InteractorModule
import com.example.myapplication.di.RepositoryModule
import com.example.myapplication.di.ResourcesModule
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