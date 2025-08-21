package com.ashashuni.salat.widget

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.unit.dp
import com.ashashuni.salat.data.ApiResponse
import com.squareup.moshi.Moshi

private val Context.widgetStore by preferencesDataStore("salat_cache")

class SalatWidget : GlanceAppWidget() {
    override suspend fun Content() {
        val context = LocalContext.current
        val prefs = currentState<androidx.datastore.preferences.core.Preferences>()
        val key = stringPreferencesKey("json")
        val json = prefs[key]
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(ApiResponse::class.java)
        val resp = json?.let { adapter.fromJson(it) }

        Column(modifier = GlanceModifier.padding(12.dp)) {
            Text("আশাশুনি নামাজ", style = TextStyle(color = ColorProvider(0xFF000000)))
            Spacer(GlanceModifier.height(6.dp))
            if (resp == null) {
                Text("ডেটা অনুপলব্ধ")
            } else {
                Row(GlanceModifier.fillMaxWidth()) {
                    Text("ফজর: ${resp.data.fajar18.short}")
                    Spacer(GlanceModifier.defaultWeight())
                    Text("যোহর: ${resp.data.noon.short}")
                }
                Row(GlanceModifier.fillMaxWidth()) {
                    Text("আসর: ${resp.data.asar1.short}")
                    Spacer(GlanceModifier.defaultWeight())
                    Text("মাগরিব: ${resp.data.set.short}")
                }
                Row(GlanceModifier.fillMaxWidth()) {
                    Text("ইশা: ${resp.data.esha.short}")
                    Spacer(GlanceModifier.defaultWeight())
                    Text("ইশরাক: ${resp.data.ishraq.short}")
                }
            }
        }
    }
}
