package com.bogdan.motivation.data.repositories

import android.util.Log
import com.bogdan.motivation.data.api.RetrofitConfiguration
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.data.entities.Themes

class QuotesApiRepository {

//    fun getQuotesFromApi() {
//        val retrofitData = RetrofitConfiguration.configureQuotesApi().getQuotesList()
//
//        retrofitData.enqueue(object : Callback<List<Root>?> {
//            override fun onResponse(call: Call<List<Root>?>, response: Response<List<Root>?>) {
//                val quotesListFromApi = response.body()
//                quotesListFromApi?.forEach {
//                    RepositoryProvider.dbRepository.addQuote(
//                        Quote(it.id, it.quote, it.author, false, Themes.LETTING_GO)
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<List<Root>?>, t: Throwable) {
//                Log.w("Get Quotes Method", t)
//            }
//        })
//    }

    suspend fun getQuotesFromApi() {
        val response = RetrofitConfiguration.configureQuotesApi().getQuotesList()
        if (response.isSuccessful) {
            val data = response.body()
            val quotesList = ArrayList<Quote>()
            data?.forEach {
                quotesList.add(
                    Quote(it.id, it.quote, it.quote, false, Themes.LETTING_GO)
                )
            }
            RepositoryProvider.dbRepository.addAllQuotes(quotesList)
        } else {
            Log.w("Get Quotes Method", response.errorBody().toString())
        }
    }
}