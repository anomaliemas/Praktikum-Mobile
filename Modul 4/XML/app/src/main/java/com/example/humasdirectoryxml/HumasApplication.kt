package com.example.humasdirectoryxml

import android.app.Application
import timber.log.Timber

class HumasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}