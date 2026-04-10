package com.example.calculatetip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatetip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        (binding.tipOptions as? AutoCompleteTextView)?.setAdapter(adapter)

        viewModel.tipResult.observe(this) { result ->
            binding.tipResult.text = "Tip Amount: $result"
        }

        binding.costOfService.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performInstantCalculation()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        (binding.tipOptions as? AutoCompleteTextView)?.setOnItemClickListener { _, _, _, _ ->
            performInstantCalculation()
        }

        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            performInstantCalculation()
        }
    }

    private fun performInstantCalculation() {
        val costString = binding.costOfService.text.toString()
        val selectedOption = binding.tipOptions.text.toString()

        val percentage = when (selectedOption) {
            "20%" -> 0.20
            "18%" -> 0.18
            else -> 0.15
        }

        val roundUp = binding.roundUpSwitch.isChecked
        viewModel.calculateTip(costString, percentage, roundUp)
    }
}