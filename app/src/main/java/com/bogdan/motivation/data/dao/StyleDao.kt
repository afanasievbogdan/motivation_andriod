package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.helpers.Styles

@Dao
interface StyleDao {
    // TODO: 15.05.2021 название функций поменяй на insert и get/fetch например
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStyle(style: Style)

    // TODO: 15.05.2021 поменяй название таблицы и модельки + вынеси в константы название таблицы
    @Query("SELECT style FROM Styles WHERE id = 1")
    suspend fun getStyle(): Styles
}