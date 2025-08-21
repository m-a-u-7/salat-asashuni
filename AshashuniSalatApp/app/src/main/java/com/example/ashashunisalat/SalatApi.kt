package com.example.ashashunisalat

import retrofit2.http.GET
import retrofit2.http.Query

data class SalatResponse(
    val date: String,
    val data: Map<String, SalatTime>
)

data class SalatTime(
    val short: String,
    val long: String
)

interface SalatApi {
    @GET("times/22.5500,89.1681")
    suspend fun getTimes(@Query("date") date: String? = null): SalatResponse
}