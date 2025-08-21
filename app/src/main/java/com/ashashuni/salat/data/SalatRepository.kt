package com.ashashuni.salat.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import com.squareup.moshi.Moshi

private val Context.dataStore by preferencesDataStore("salat_cache")

class SalatRepository(private val context: Context) {
    private val api = SalatApiService.create()
    private val KEY_JSON = stringPreferencesKey("json")
    private val moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(ApiResponse::class.java)

    suspend fun fetch(lat: String, lon: String): Result<ApiResponse> = try {
        val resp = api.getTimes(lat = lat, lon = lon)
        val json = adapter.toJson(resp)
        context.dataStore.edit { it[KEY_JSON] = json }
        Result.success(resp)
    } catch (t: Throwable) {
        // Fallback to cache
        val cached = context.dataStore.data.first()[KEY_JSON]?.let { adapter.fromJson(it) }
        if (cached != null) Result.success(cached) else Result.failure(t)
    }

    suspend fun cached(): ApiResponse? =
        context.dataStore.data.first()[KEY_JSON]?.let { adapter.fromJson(it) }
}
