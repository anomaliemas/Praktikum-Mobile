package com.example.humasdirectorycompose

import android.app.Application
import timber.log.Timber

class HumasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi Timber
        Timber.plant(Timber.DebugTree())
    }
}