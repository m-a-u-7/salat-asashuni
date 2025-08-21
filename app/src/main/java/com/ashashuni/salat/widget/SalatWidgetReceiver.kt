
package com.ashashuni.salat.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.ashashuni.salat.R
import com.ashashuni.salat.data.SalatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SalatWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_salat)
            val intent = Intent(context, SalatWidgetReceiver::class.java).apply {
                action = "com.ashashuni.salat.action.REFRESH"
            }
            val pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.btn_refresh, pi)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        refresh(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "com.ashashuni.salat.action.REFRESH") {
            Toast.makeText(context, "Refreshing…", Toast.LENGTH_SHORT).show()
            refresh(context)
        }
    }

    private fun refresh(context: Context) {
        val mgr = AppWidgetManager.getInstance(context)
        val cn = ComponentName(context, SalatWidgetReceiver::class.java)
        val ids = mgr.getAppWidgetIds(cn)
        val views = RemoteViews(context.packageName, R.layout.widget_salat)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = SalatApi.service.getTimes(22.5500, 89.1681)
                views.setTextViewText(R.id.widget_title, "আশাশুনি নামাজের সময়")
                views.setTextViewText(R.id.widget_date, res.date)
                views.setTextViewText(R.id.widget_fajr, "ফজর: ${res.data.fajar18.short}")
                views.setTextViewText(R.id.widget_dhuhr, "যোহর: ${res.data.noon.short}")
                views.setTextViewText(R.id.widget_asr, "আসর: ${res.data.asar1.short}")
                views.setTextViewText(R.id.widget_maghrib, "মাগরিব: ${res.data.set.short}")
                views.setTextViewText(R.id.widget_isha, "এশা: ${res.data.esha.short}")
            } catch (t: Throwable) {
                views.setTextViewText(R.id.widget_date, "ত্রুটি: " + (t.message ?: "অজানা"))
            } finally {
                ids.forEach { id -> mgr.updateAppWidget(id, views) }
            }
        }
    }
}
