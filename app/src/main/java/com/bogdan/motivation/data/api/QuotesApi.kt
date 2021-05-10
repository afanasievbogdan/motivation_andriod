package com.bogdan.motivation.data.api

import com.bogdan.motivation.data.entities.Root
import retrofit2.Call
import retrofit2.http.GET

interface QuotesApi {

    @GET("./QuotesInfo")
    fun getQuotesList(): Call<List<Root>>
}