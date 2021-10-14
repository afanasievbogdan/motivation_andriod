package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.QuoteDao
import com.bogdan.motivation.data.entities.local.CommentsList
import com.bogdan.motivation.data.entities.local.FavQuotesList
import com.bogdan.motivation.data.entities.local.Quote
import com.bogdan.motivation.data.entities.local.QuotesList
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val quoteDao: QuoteDao) {

    suspend fun insertAllQuotes(quotes: List<Quote>) = quoteDao.insertAllQuotes(quotes)

    suspend fun getAllQuotes(): QuotesList = QuotesList(quoteDao.getAllQuotes())

    suspend fun getAllComments(): CommentsList {
        val list = QuotesList(quoteDao.getAllQuotes())

        val commentsList = mutableListOf<String>()
        list.quotes.forEach {
            commentsList.addAll(it.comments)
        }
        return CommentsList(commentsList)
    }

    suspend fun getFavoriteQuotes(): QuotesList = QuotesList(quoteDao.getFavoriteQuotes())

    suspend fun getFavoriteQuotesContent(): FavQuotesList {

        val list = QuotesList(quoteDao.getFavoriteQuotes())

        val quotesContentList = mutableListOf<String>()
        list.quotes.forEach {
            quotesContentList.add(it.quote)
        }
        return FavQuotesList(quotesContentList)
    }

    suspend fun getRandomQuote(): Quote = quoteDao.getRandomQuote()

    suspend fun updateQuote(quote: String, favorite: Boolean) =
        quoteDao.updateQuote(quote, favorite)

    suspend fun updateQuoteComments(quote: String, comments: List<String>) =
        quoteDao.updateQuoteComments(quote, comments)

    suspend fun getQuote(quoteName: String) = quoteDao.getQuote(quoteName)
}