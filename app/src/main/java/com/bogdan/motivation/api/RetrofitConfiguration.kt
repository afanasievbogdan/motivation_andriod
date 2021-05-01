package com.bogdan.motivation.api

import com.bogdan.motivation.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfiguration {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    private fun configureOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpBuilder.build()
    }

    private fun configureRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(configureOkHttp())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun configureQuotesApi(): QuotesApi = configureRetrofit().create(QuotesApi::class.java)
}