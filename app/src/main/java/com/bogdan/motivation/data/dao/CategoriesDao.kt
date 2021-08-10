package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Categories
import com.bogdan.motivation.helpers.Categories as CategoriesEnum

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: Categories)

    @Query("SELECT category FROM Categories")
    suspend fun getCategory(): List<CategoriesEnum>
}