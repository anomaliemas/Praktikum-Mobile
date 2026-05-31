package com.example.catapp.cat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catapp.cat.data.CatRepository
import com.example.catapp.core.SharedPrefsHelper

class CatViewModelFactory(
    private val repository: CatRepository,
    private val prefsHelper: SharedPrefsHelper,
    private val title: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            return CatViewModel(repository, prefsHelper, title) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}