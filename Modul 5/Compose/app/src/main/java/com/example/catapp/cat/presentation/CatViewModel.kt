package com.example.catapp.cat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.cat.data.CatRepository
import com.example.catapp.core.common.UiState
import com.example.catapp.core.database.CatEntity
import com.example.catapp.core.SharedPrefsHelper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CatViewModel(
    private val repository: CatRepository,
    private val prefsHelper: SharedPrefsHelper,
    private val screenTitle: String
) : ViewModel() {

    val uiState: StateFlow<UiState<List<CatEntity>>> = repository.catBreeds
        .map { cats ->
            if (cats.isEmpty()) {
                UiState.Loading
            } else {
                UiState.Success(cats)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    private val _userName = MutableStateFlow(prefsHelper.getUserName())
    val userName: StateFlow<String> = _userName

    init {
        fetchCatDataFromNetwork()
    }

    private fun fetchCatDataFromNetwork() {
        viewModelScope.launch {
            try {
                repository.fetchBreedsFromNetwork()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun saveName(name: String) {
        prefsHelper.saveUserName(name)
        _userName.value = name
    }

    fun getScreenTitle() = screenTitle

    fun logDetailClick(name: String) {
        Timber.d("Timber: Tombol Detail ditekan")
        Timber.d("Timber: Data dipilih berpindah ke detail -> $name")
    }

    fun logExplicitClick() {
        Timber.d("Timber: Tombol Intent Eksplisit ditekan")
    }
}