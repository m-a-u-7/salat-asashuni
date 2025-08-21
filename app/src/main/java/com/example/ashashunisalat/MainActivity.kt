package com.example.ashashunisalat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ashashunisalat.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SalatScreen()
                }
            }
        }
    }
}

@Composable
fun SalatScreen(vm: SalatViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ashashuni - Today's Salat Times", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text("Error: ${state.error}")
        } else {
            LazyColumn {
                items(state.times) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(item.name, style = MaterialTheme.typography.bodyLarge)
                            Text(item.time, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}