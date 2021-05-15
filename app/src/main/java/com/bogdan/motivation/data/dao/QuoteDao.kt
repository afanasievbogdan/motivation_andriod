package com.bogdan.motivation.data.dao

import androidx.room.*
import com.bogdan.motivation.data.entities.Quote
import retrofit2.http.Query

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM Quotes")
    suspend fun readAllQuotes(): List<Quote>

    @Query("SELECT * FROM Quotes WHERE _favorite = 1")
    suspend fun readFavoriteQuotes(): List<Quote>

    // TODO: 15.05.2021 почему не suspend?
    @Query("SELECT * FROM Quotes ORDER BY RANDOM() LIMIT 1")
    fun readRandomQuote(): Quote

    @Query("UPDATE Quotes SET _favorite = :favorite WHERE _quote = :quote")
    suspend fun updateQuote(quote: String, favorite: Boolean)
}