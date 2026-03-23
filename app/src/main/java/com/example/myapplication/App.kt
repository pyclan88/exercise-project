package com.example.myapplication

import android.app.Application
import com.example.myapplication.AppComponent
import com.example.myapplication.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }
}