package com.bogdan.motivation.data.dao

import androidx.room.*
import com.bogdan.motivation.data.entities.local.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM Quotes ORDER BY RANDOM() LIMIT 32")
    suspend fun getAllQuotes(): List<Quote>

    @Query("SELECT * FROM Quotes WHERE favorite = 1")
    suspend fun getFavoriteQuotes(): List<Quote>

    @Query("SELECT * FROM Quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote

    @Query("UPDATE Quotes SET favorite = :favorite WHERE id = (SELECT id FROM Quotes WHERE quote = :quote)")
    suspend fun updateQuote(quote: String, favorite: Boolean)
}