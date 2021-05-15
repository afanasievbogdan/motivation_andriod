package com.bogdan.motivation.data.api

import com.bogdan.motivation.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfiguration {

    // TODO: 15.05.2021 вынести в отдельный file object Constants
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // TODO: 15.05.2021 configure замени на create
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