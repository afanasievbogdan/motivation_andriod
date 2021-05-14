package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.CurrentStyle
import com.bogdan.motivation.data.entities.Styles

@Dao
interface CurrentStyleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentStyle(currentStyle: CurrentStyle)

    @Query("SELECT _style FROM Styles WHERE id = 1")
    suspend fun readCurrentStyle(): Styles
}