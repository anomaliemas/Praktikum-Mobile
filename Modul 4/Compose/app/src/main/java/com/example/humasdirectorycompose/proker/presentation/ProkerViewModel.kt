package com.example.humasdirectorycompose.proker.presentation

import com.example.humasdirectorycompose.proker.data.Proker
import com.example.humasdirectorycompose.proker.data.ProkerData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class ProkerViewModel(private val screenTitle: String) : ViewModel() {

    private val _prokerList = MutableStateFlow<List<Proker>>(emptyList())
    val prokerList: StateFlow<List<Proker>> = _prokerList.asStateFlow()

    init {
        loadProkerData()
    }

    private fun loadProkerData() {
        _prokerList.value = ProkerData.listData
        Timber.d("Timber: Berhasil memuat ${_prokerList.value.size} data ke dalam StateFlow")
    }

    fun getScreenTitle() = screenTitle

    fun logDetailClick(name: String) {
        Timber.d("Timber: Tombol Detail ditekan")
        Timber.d("Timber: Data dipilih berpindah ke detail -> $name")
    }

    fun logExplicitClick() {
        Timber.d("Timber: Tombol Instagram (Explicit Intent) ditekan")
    }
}