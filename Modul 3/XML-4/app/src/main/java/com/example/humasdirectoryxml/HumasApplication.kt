package com.example.humasdirectoryxml

import android.app.Application
import timber.log.Timber

class HumasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}