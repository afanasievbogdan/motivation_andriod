package com.bogdan.motivation.data.api

import com.bogdan.motivation.BuildConfig
import com.bogdan.motivation.helpers.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfiguration {
    // TODO: 15.05.2021 вынести в отдельный file object Constants
    // TODO: 15.05.2021 configure замени на create
    private fun createOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpBuilder.build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(createOkHttp())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun createQuotesApiInstance(): QuotesApi = createRetrofit().create(QuotesApi::class.java)
}