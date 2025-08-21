package com.ashashuni.salat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.ashashuni.salat.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    private val vm: SalatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SalatScreen(vm)
                }
            }
        }
    }
}
