package com.bogdan.motivation.api

import android.util.Log
import com.bogdan.motivation.BuildConfig
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Root
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfiguration {

    private lateinit var retrofit: QuotesApi
    private const val BASE_URL = "http://10.0.2.2:3000/"

    private fun configureOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        TODO: тут должна быть проверка на дебаг/релиз версию, чтобы не логгировались запросы в релизной версии
//        TODO: там где добавляешь интерцептор
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun configureRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(configureOkHttp())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

//    TODO: должно быть так
//    fun configureQuotesApi(): QuotesApi = configureRetrofit().create(QuotesApi::class.java)
//
//    val retrofitData = configureQuotesApi().getQuotesList()

    fun configureQuotesApi() {
        retrofit = configureRetrofit().create(QuotesApi::class.java)
    }

//    TODO: нужно вынести это из этого класса (в активити или читай паттерн репозиторий)
    fun getQuotesFromApi(dbManager: DBManager) {
        val retrofitData = retrofit.getQuotesList()

        retrofitData.enqueue(object : Callback<List<Root>?> {
            override fun onResponse(call: Call<List<Root>?>, response: Response<List<Root>?>) {
                val quotesListFromApi = response.body()
                quotesListFromApi?.forEach {
                    dbManager.insetToQuotesDb(it.quote, it.author, "0")
                }
            }

            override fun onFailure(call: Call<List<Root>?>, t: Throwable) {
                Log.w("Get Quotes Method", t)
            }
        })
    }
}