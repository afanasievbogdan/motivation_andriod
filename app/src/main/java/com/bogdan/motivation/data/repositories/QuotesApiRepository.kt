package com.bogdan.motivation.data.repositories

import android.util.Log
import com.bogdan.motivation.data.api.QuotesApi
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.remote.ApiQuote
import javax.inject.Inject
import com.bogdan.motivation.helpers.Themes as ThemesEnum

class QuotesApiRepository @Inject constructor(
    private val quotesApi: QuotesApi,
    private val quotesRepository: QuotesRepository,
    private val themesRepository: ThemesRepository
) {

    suspend fun getQuotesFromApi() {
        try {
            val response = quotesApi.getQuotesList()
            if (response.isSuccessful) {
                val apiList = response.body()

                val quotesList = fillQuotesList(apiList)
                quotesRepository.insertAllQuotes(quotesList)
            } else {
                Log.w("DEBUG", "getQuotesFromApi() Wrong Response" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.w("DEBUG", "getQuotesFromApi() Wrong Mapping $e")
        }
    }

    private suspend fun fillQuotesList(apiList: List<ApiQuote>?): List<Quote> {
        val quotesList = ArrayList<Quote>()
        val pickedThemes = themesRepository.getTheme()
        apiList?.forEach {
            try {
                if (pickedThemes.contains(ThemesEnum.valueOf(it.theme))) {
                    quotesList.add(
                        Quote(it.id, it.quote, it.quote, false, ThemesEnum.valueOf(it.theme))
                    )
                }
            } catch (e: Exception) {
                Log.i("fillQuotesList", "No such theme $e")
            }
        }
        return quotesList
    }
}