package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Utils

@Dao
interface UtilsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUtils(utils: Utils)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUtils(utils: Utils)

    @Query("SELECT * FROM Utils WHERE id = 1")
    suspend fun getUtils(): Utils
}