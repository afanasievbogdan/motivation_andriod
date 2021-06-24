package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.helpers.Styles

@Dao
interface StyleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStyle(style: Style)

    @Query("SELECT style FROM Styles WHERE id = 1")
    suspend fun getStyle(): Styles
}