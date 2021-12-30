package com.example.filmsearch

import android.app.Application
import com.example.filmsearch.di.AppComponent
import com.example.filmsearch.di.DaggerAppComponent

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    companion object {
        lateinit var component: AppComponent
    }
}