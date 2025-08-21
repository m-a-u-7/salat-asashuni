package com.ashashuni.salat.data

import com.squareup.moshi.Json

data class ApiResponse(
    val lat: String,
    val lon: String,
    val tzname: String,
    val tz: Long,
    val date: String,
    val qibla: Double,
    val data: Times
)

data class Times(
    val fajar18: Entry,
    val rise: Entry,
    val noon: Entry,
    val asar1: Entry,
    val asar2: Entry,
    val set: Entry,
    val esha: Entry,
    val ishraq: Entry,
    val sehri: Entry,
    val asarend: Entry,
    val setstart: Entry,
    val night1: Entry,
    val midnight: Entry,
    val night2: Entry,
    val night6: Entry,
    @Json(name = "magrib12") val magrib12: Entry? = null
)

data class Entry(
    val short: String,
    val long: String,
    val secs: Double
)
