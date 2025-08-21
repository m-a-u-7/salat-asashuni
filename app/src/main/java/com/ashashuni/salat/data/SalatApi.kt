
package com.ashashuni.salat.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object SalatApi {
    private const val BASE_URL = "https://salat.habibur.com/"

    data class TimeEntry(val short: String, val long: String, val secs: Double)
    data class Times(
        val fajar18: TimeEntry,
        val rise: TimeEntry,
        val noon: TimeEntry,
        val asar1: TimeEntry,
        val asar2: TimeEntry? = null,
        val set: TimeEntry,
        val magrib12: TimeEntry? = null,
        val esha: TimeEntry,
        val night1: TimeEntry? = null,
        val midnight: TimeEntry? = null,
        val night2: TimeEntry? = null,
        val night6: TimeEntry? = null,
        val sehri: TimeEntry? = null,
        val setstart: TimeEntry? = null,
        val ishraq: TimeEntry? = null,
        val asarend: TimeEntry? = null
    )
    data class SalatResponse(
        val lat: String,
        val lon: String,
        val tzname: String,
        val tz: Int,
        val date: String,
        val qibla: Double,
        val data: Times
    )

    interface Service {
        @GET("api/")
        suspend fun getTimes(@Query("lat") lat: Double, @Query("lon") lon: Double): SalatResponse
    }

    val service: Service by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Service::class.java)
    }
}
