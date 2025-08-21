package com.ashashuni.salat.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ashashuni.salat.data.ApiResponse

@Composable
fun SalatScreen(vm: SalatViewModel) {
    val state by vm.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("আশাশুনি নামাজ") },
                actions = { TextButton(onClick = vm::refresh) { Text("রিফ্রেশ") } })
        }
    ) { pad ->
        when (val s = state) {
            is SalatViewModel.UiState.Loading -> Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            is SalatViewModel.UiState.Error -> Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) { Text(s.msg) }
            is SalatViewModel.UiState.Ready -> SalatContent(s.data, Modifier.padding(pad))
        }
    }
}

@Composable
fun SalatContent(resp: ApiResponse, modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card { Column(Modifier.padding(16.dp)) {
            Text("আজ: ${resp.date}", style = MaterialTheme.typography.titleMedium)
            Text("অবস্থান: 22.5500 N, 89.1681 E", style = MaterialTheme.typography.bodyMedium)
            Text("কিবলা: ${"%.1f".format(resp.qibla)}°", style = MaterialTheme.typography.bodyMedium)
        } }
        TimeGrid("নামাজের সময়", listOf(
            "ফজর" to resp.data.fajar18.short,
            "সূর্যোদয়" to resp.data.rise.short,
            "যোহর" to resp.data.noon.short,
            "আসর (শাফি)" to resp.data.asar1.short,
            "আসর (হানাফি)" to resp.data.asar2.short,
            "মাগরিব" to resp.data.set.short,
            "ইশা" to resp.data.esha.short
        ))
        TimeGrid("অন্যান্য", listOf(
            "সেহরির শেষ" to resp.data.sehri.short,
            "ইশরাক" to resp.data.ishraq.short,
            "আসর মাকরূহ" to resp.data.asarend.short,
            "সূর্যাস্ত আরম্ভ" to resp.data.setstart.short,
            "লাল আলো শেষ" to (resp.data.magrib12?.short ?: "—"),
            "রাত ১/৩" to resp.data.night1.short,
            "মধ্যরাত" to resp.data.midnight.short,
            "শেষ ১/৩" to resp.data.night2.short,
            "শেষ রাত" to resp.data.night6.short
        ))
    }
}

@Composable
fun TimeGrid(title: String, rows: List<Pair<String, String>>) {
    Card {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            rows.forEach { (k, v) ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(k)
                    Text(v, textAlign = TextAlign.End, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
