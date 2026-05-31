package com.example.humasdirectoryxml

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class ProkerViewModel(private val userName: String) : ViewModel() {
    private val _prokerList = MutableStateFlow<List<Proker>>(emptyList())
    val prokerList: StateFlow<List<Proker>> = _prokerList.asStateFlow()

    // Variabel ini ditambahkan untuk mengatasi error "Unresolved reference"
    private val _navigateToDetail = MutableStateFlow<String?>(null)
    val navigateToDetail: StateFlow<String?> = _navigateToDetail.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val data = ProkerData.listData
        _prokerList.value = data
        Timber.d("Data item masuk ke dalam list. Pengguna: $userName")
    }

    fun onProkerClicked(prokerName: String) {
        Timber.i("Data list terpilih: $prokerName")
        _navigateToDetail.value = prokerName
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
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