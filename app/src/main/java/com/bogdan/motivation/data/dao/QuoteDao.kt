package com.bogdan.motivation.data.dao

import androidx.room.*
import com.bogdan.motivation.data.entities.local.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM Quotes")
    suspend fun getAllQuotes(): List<Quote>

    @Query("SELECT * FROM Quotes WHERE favorite = 1")
    suspend fun getFavoriteQuotes(): List<Quote>

    // TODO: 15.05.2021 почему не suspend?
    @Query("SELECT * FROM Quotes ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote(): Quote

    @Query("UPDATE Quotes SET favorite = :favorite WHERE quote = :quote")
    suspend fun updateQuote(quote: String, favorite: Boolean)
}