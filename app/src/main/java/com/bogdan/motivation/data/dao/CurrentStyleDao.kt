package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.CurrentStyle
import com.bogdan.motivation.data.entities.Styles
import retrofit2.http.Query

@Dao
interface CurrentStyleDao {
    // TODO: 15.05.2021 название функций поменяй на insert и get/fetch например
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentStyle(currentStyle: CurrentStyle)

    // TODO: 15.05.2021 поменяй название таблицы и модельки + вынеси в константы название таблицы
    @Query("SELECT _style FROM Styles WHERE id = 1")
    suspend fun readCurrentStyle(): Styles
}