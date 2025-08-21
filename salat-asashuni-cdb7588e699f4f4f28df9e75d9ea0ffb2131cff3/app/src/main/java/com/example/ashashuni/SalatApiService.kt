package com.ashashuni.salat.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SalatApiService {
    @GET("api/")
    suspend fun getTimes(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("tzoffset") tzoffset: Int = 360,
        @Query("tzname") tzname: String = "Asia/Dhaka"
    ): ApiResponse

    companion object {
        fun create(): SalatApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            return Retrofit.Builder()
                .baseUrl("https://salat.habibur.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
                .create(SalatApiService::class.java)
        }
    }
}
