package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.Themes as ThemesEnum

@Entity(tableName = Constants.TABLE_THEMES)
data class Themes(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "theme")
    val theme: ThemesEnum
)
