package com.bogdan.motivation.data.api

import com.bogdan.motivation.data.entities.remote.ApiQuote
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {

    @GET("./QuotesInfo")
    suspend fun getQuotesList(): Response<List<ApiQuote>>
}