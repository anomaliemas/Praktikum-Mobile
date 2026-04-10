package com.example.calculatetip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.ceil

class MainViewModel : ViewModel() {
    private val _tipResult = MutableLiveData<String>()
    val tipResult: LiveData<String> = _tipResult

    fun calculateTip(costString: String, percentage: Double, roundUp: Boolean) {
        val cost = costString.toDoubleOrNull()

        if (cost == null || cost == 0.0) {
            _tipResult.value = "$0.00"
            return
        }

        var tip = cost * percentage

        if (roundUp) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        _tipResult.value = formattedTip
    }
}