package com.bogdan.motivation.data.repositories

import android.util.Log
import com.bogdan.motivation.data.api.RetrofitConfiguration
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.remote.ApiQuote
import com.bogdan.motivation.helpers.Themes
import java.lang.Exception

class QuotesApiRepository {
    // TODO: 15.05.2021 вынеси маппинг в отдельную функцию
    // TODO: 15.05.2021 сделай обработку ошибок try catch
    suspend fun getQuotesFromApi() {
        val response = RetrofitConfiguration.createQuotesApiInstance().getQuotesList()
        if (response.isSuccessful) {
            val apiList = response.body()
            try {
                val quotesList = fillQuotesList(apiList)
                RepositoryProvider.quotesRepository.insertAllQuotes(quotesList)
            } catch (e: Exception) {
                Log.w("Get Quotes Method", "Wrong Response")
            }
        } else {
            Log.w("Get Quotes Method", response.errorBody().toString())
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