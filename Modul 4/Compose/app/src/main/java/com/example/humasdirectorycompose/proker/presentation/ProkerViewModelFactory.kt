package com.example.humasdirectorycompose.proker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProkerViewModelFactory(private val title: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProkerViewModel(title) as T
    }
}