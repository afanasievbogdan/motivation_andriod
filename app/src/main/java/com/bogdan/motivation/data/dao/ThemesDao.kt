package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Themes
import com.bogdan.motivation.helpers.Themes as ThemesEnum

@Dao
interface ThemesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(themes: Themes)

    @Query("SELECT theme FROM Themes")
    suspend fun getTheme(): List<ThemesEnum>
}