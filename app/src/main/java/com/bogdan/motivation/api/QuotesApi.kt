package com.bogdan.motivation.api

import com.bogdan.motivation.entities.Root
import retrofit2.Call
import retrofit2.http.GET

interface QuotesApi {

    @GET("./QuotesInfo")
    fun getQuotesList(): Call<List<Root>>
}
