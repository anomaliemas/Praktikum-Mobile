package com.example.catapp.core

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("cat_prefs", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        prefs.edit().putString("USER_NAME", name).apply()
    }

    fun getUserName(): String {
        return prefs.getString("USER_NAME", "Pecinta Kucing") ?: "Pecinta Kucing"
    }
}