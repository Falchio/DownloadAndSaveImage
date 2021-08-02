package com.example.saveimageindatabase

import android.app.Application

class App:Application() {
    val INSTANSE:App
        get() {
            return this
        }
}