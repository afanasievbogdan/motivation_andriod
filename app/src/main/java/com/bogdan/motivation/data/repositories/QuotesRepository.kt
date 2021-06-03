package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Quote

class QuotesRepository {

    lateinit var db: ApplicationDatabase

    suspend fun insertAllQuotes(quotes: List<Quote>) = db.quoteDao().insertAllQuotes(quotes)

    suspend fun getAllQuotes(): List<Quote> = db.quoteDao().getAllQuotes()

    suspend fun getFavoriteQuotes(): List<Quote> = db.quoteDao().getFavoriteQuotes()

    fun getRandomQuote(): Quote = db.quoteDao().getRandomQuote()

    suspend fun updateQuote(quote: String, favorite: Boolean) = db.quoteDao().updateQuote(quote, favorite)
}