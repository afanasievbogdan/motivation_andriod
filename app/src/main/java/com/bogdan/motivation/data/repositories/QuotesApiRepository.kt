package com.bogdan.motivation.data.repositories

import android.util.Log
import com.bogdan.motivation.data.api.QuotesApi
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.remote.ApiQuote
import com.bogdan.motivation.helpers.Themes
import javax.inject.Inject

class QuotesApiRepository @Inject constructor(
    private val quotesApi: QuotesApi,
    private val quotesRepository: QuotesRepository
) {

    suspend fun getQuotesFromApi() {
        try {
            val response = quotesApi.getQuotesList()
            if (response.isSuccessful) {
                val apiList = response.body()

                val quotesList = fillQuotesList(apiList)
                quotesRepository.insertAllQuotes(quotesList)
            } else {
                Log.w("DEBUG", "getQuotesFromApi() Method Wrong Response" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.w("DEBUG", "getQuotesFromApi() Method Wrong Mapping $e")
        }
    }

    private fun fillQuotesList(apiList: List<ApiQuote>?): List<Quote> {
        val quotesList = ArrayList<Quote>()
        apiList?.forEach {
            quotesList.add(
                Quote(it.id, it.quote, it.quote, false, Themes.valueOf(it.theme))
            )
        }
        return quotesList
    }
}