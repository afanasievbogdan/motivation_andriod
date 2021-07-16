package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.QuoteDao
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.QuotesList
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val quoteDao: QuoteDao) {

    suspend fun insertAllQuotes(quotes: List<Quote>) = quoteDao.insertAllQuotes(quotes)

    suspend fun getAllQuotes(): QuotesList = QuotesList(quoteDao.getAllQuotes())

    suspend fun getFavoriteQuotes(): QuotesList = QuotesList(quoteDao.getFavoriteQuotes())

    suspend fun getRandomQuote(): Quote = quoteDao.getRandomQuote()

    suspend fun updateQuote(quote: String, favorite: Boolean) = quoteDao.updateQuote(quote, favorite)
}