package com.project.livegreen.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.livegreen.models.Carbon
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.co2signal.com/v1/"
private const val API_KEY =  "99860c1128750754"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun logInterceptor() : HttpLoggingInterceptor {
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return logInterceptor
}

private val client = OkHttpClient.Builder()
    .addInterceptor(logInterceptor())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(BASE_URL)
    .build()


interface CarbonService {
    @Headers("auth-token: $API_KEY")
    @GET("latest")
    suspend fun getCarbonData(@Query("lon") lon : Double, @Query("lat") lat : Double) : Carbon
}

object CarbonApi {
    val carbonService : CarbonService by lazy {
        retrofit.create(CarbonService::class.java)
    }
}