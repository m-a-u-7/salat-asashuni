
package com.ashashuni.salat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ashashuni.salat.data.SalatApi
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    SalatScreen()
                }
            }
        }
    }
}

@Composable
fun SalatScreen() {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var times by remember { mutableStateOf<SalatApi.SalatResponse?>(null) }

    fun load() {
        scope.launch {
            loading = true; error = null
            try { times = SalatApi.service.getTimes(22.5500, 89.1681) } catch (t: Throwable) { error = t.message }
            loading = false
        }
    }

    LaunchedEffect(Unit) { load() }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("আশাশুনি নামাজের সময়", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("ত্রুটি: ${error}")
            Spacer(Modifier.height(8.dp))
            Button(onClick = { load() }) { Text("পুনরায় চেষ্টা") }
        } else {
            times?.let { res ->
                Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("তারিখ: ${res.date}  (Qibla: ${"%.1f".format(res.qibla)}°)")
                        Spacer(Modifier.height(8.dp))
                        fun row(label:String, value:String) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(label); Text(value, fontWeight = FontWeight.SemiBold)
                            }
                        }
                        row("ফজর", res.data.fajar18.short)
                        row("সূর্যোদয়", res.data.rise.short)
                        row("যোহর", res.data.noon.short)
                        row("আসর", res.data.asar1.short)
                        row("মাগরিব", res.data.set.short)
                        row("এশা", res.data.esha.short)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { load() }) { Text("রিফ্রেশ") }
                    }
                }
            } ?: Text("ডেটা পাওয়া যায়নি")
        }
    }
}
