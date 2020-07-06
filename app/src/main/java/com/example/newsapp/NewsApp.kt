package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.databaseModule
import com.example.newsapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, databaseModule)
        }
    }
}