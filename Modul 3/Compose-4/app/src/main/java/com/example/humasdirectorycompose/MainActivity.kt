package com.example.humasdirectorycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.humasdirectorycompose.ui.theme.HumasDirectoryComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HumasDirectoryComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Inisialisasi ViewModel
                    val prokerViewModel: ProkerViewModel = viewModel(
                        factory = ProkerViewModelFactory("Ahmad Luthfi Maulana")
                    )

                    // Memanggil file navigasi
                    HumasNavigation(
                        navController = navController,
                        viewModel = prokerViewModel
                    )
                }
            }
        }
    }
}