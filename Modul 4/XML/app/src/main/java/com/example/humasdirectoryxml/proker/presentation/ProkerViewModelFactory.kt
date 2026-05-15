package com.example.humasdirectoryxml.proker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProkerViewModelFactory(private val title: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProkerViewModel::class.java)) {
            return ProkerViewModel(title) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}