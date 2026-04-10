package com.example.calculatetip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class MainViewModel : ViewModel() {
    // State untuk menyimpan hasil perhitungan tip
    private val _tipResult = MutableLiveData<String>()
    val tipResult: LiveData<String> = _tipResult

    // Logika perhitungan tip sesuai persyaratan [cite: 13, 16]
    fun calculateTip(costString: String, percentage: Double, roundUp: Boolean) {
        val cost = costString.toDoubleOrNull() ?: 0.0 // Menangani input tidak valid [cite: 14]
        var tip = cost * percentage

        // Fitur pembulatan tip [cite: 20]
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        // Format hasil ke dalam mata uang [cite: 21]
        _tipResult.value = NumberFormat.getCurrencyInstance().format(tip)
    }
}