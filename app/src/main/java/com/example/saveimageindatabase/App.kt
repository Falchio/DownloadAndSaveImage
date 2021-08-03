package com.example.saveimageindatabase

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private lateinit var appContext: App
        fun getAppContext(): Context {
            return appContext.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}