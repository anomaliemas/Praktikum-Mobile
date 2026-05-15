package com.example.humasdirectorycompose

import android.app.Application
import timber.log.Timber

class HumasComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}