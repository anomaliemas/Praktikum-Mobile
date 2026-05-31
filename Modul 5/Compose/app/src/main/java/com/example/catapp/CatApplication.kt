package com.example.catapp

import android.app.Application
import androidx.room.Room
import com.example.catapp.cat.data.CatApiService
import com.example.catapp.cat.data.CatRepository
import com.example.catapp.core.network.ApiClient
import com.example.catapp.core.database.CatDatabase

class CatApplication : Application() {
    lateinit var database: CatDatabase
    lateinit var repository: CatRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            CatDatabase::class.java,
            "cat_database"
        ).build()

        val apiService = ApiClient.apiService
        val repository = CatRepository(apiService, database.catDao())
    }
}