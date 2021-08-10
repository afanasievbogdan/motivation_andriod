package com.bogdan.motivation.data.repositories

import android.util.Log
import com.bogdan.motivation.data.api.QuotesApi
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.remote.ApiQuote
import javax.inject.Inject
import com.bogdan.motivation.helpers.Categories as CategoriesEnum

class QuotesApiRepository @Inject constructor(
    private val quotesApi: QuotesApi,
    private val quotesRepository: QuotesRepository,
    private val categoriesRepository: CategoriesRepository
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
        val pickedThemes = categoriesRepository.getCategory()
        apiList?.forEach {
            try {
                if (pickedThemes.contains(CategoriesEnum.valueOf(it.theme ?: CategoriesEnum.motavation.name))) {
                    quotesList.add(
                        Quote(
                            0,
                            it.content ?: " ",
                            it.author ?: " ",
                            false,
                            CategoriesEnum.valueOf(it.theme ?: CategoriesEnum.motavation.name)
                        )
                    )
                }
            } catch (e: Exception) {
                Log.i("DEBUG", "fillQuotesList() No such theme $e")
            }
        }
        return quotesList
    }
}