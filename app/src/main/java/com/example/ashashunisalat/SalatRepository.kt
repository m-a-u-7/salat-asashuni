package com.example.ashashunisalat

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SalatRepository {
    private val api: SalatApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://salat.habibur.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(SalatApi::class.java)
    }

    suspend fun fetchTimes(): SalatResponse = api.getTimes()
}