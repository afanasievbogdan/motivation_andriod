package com.bogdan.motivation.api

import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Root
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfiguration {

    fun configureRetrofit(dbManager : DBManager){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(DateAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(QuotesApi::class.java)

        val retrofitData = retrofit.getQuotesList()

        retrofitData.enqueue(object : Callback<List<Root>?> {
            override fun onResponse(call: Call<List<Root>?>, response: Response<List<Root>?>) {
                val quotesListFromApi = response.body()
                quotesListFromApi?.forEach {
                    dbManager.insetToQuotesDb(it.quote, it.author, "0")
                }
            }

            override fun onFailure(call: Call<List<Root>?>, t: Throwable) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            }
        })
    }
}