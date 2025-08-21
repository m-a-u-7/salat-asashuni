package com.example.ashashunisalat

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import androidx.glance.layout.Column
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SalatWidget : GlanceAppWidget() {
    override suspend fun Content() {
        Column {
            Text("Ashashuni Salat Times")
            Text("Fajr: --", style = androidx.glance.text.TextStyle(color = ColorProvider(android.graphics.Color.WHITE)))
            Text("Dhuhr: --")
            Text("Asr: --")
            Text("Maghrib: --")
            Text("Isha: --")
        }
    }
}

class SalatWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SalatWidget()
}