package com.example.humasdirectorycompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class ProkerViewModel(private val userName: String) : ViewModel() {
    private val _prokerList = MutableStateFlow<List<Proker>>(emptyList())
    val prokerList: StateFlow<List<Proker>> = _prokerList.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        // Mengambil list data dari ProkerData
        val data = ProkerData.listData
        _prokerList.value = data
        Timber.d("Data item masuk ke dalam list Compose. Pengguna: $userName")
    }

    fun logExplicitIntent(prokerName: String) {
        Timber.i("Tombol Explicit Intent ditekan untuk: $prokerName")
    }

    fun logDetailClick(prokerName: String) {
        Timber.i("Tombol Detail ditekan untuk: $prokerName")
    }
}

class ProkerViewModelFactory(private val name: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProkerViewModel(name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}