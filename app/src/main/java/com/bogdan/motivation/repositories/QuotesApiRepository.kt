package com.bogdan.motivation.repositories

import android.util.Log
import com.bogdan.motivation.api.RetrofitConfiguration
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.entities.Root
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesApiRepository {

    fun getQuotesFromApi(dbManager: DBManager) {
        val retrofitData = RetrofitConfiguration.configureQuotesApi().getQuotesList()

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