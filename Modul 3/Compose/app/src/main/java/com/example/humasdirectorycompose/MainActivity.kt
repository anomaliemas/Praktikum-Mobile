package com.example.humasdirectorycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.humasdirectorycompose.ui.theme.HumasDirectoryComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HumasDirectoryComposeTheme {
                HumasNavigation()
            }
        }
    }
}