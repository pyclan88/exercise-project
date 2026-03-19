package com.example.myapplication.presentation.resources

import android.content.Context
import com.example.myapplication.di.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProviderImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ResourceProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}