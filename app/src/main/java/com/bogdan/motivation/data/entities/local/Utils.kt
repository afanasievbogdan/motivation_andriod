package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants

@Entity(tableName = Constants.TABLE_UTILS)
data class Utils(
    @PrimaryKey
    val id: Long = 1,
    @ColumnInfo(name = "isSettingsPassed")
    val isSettingsPassed: Boolean,
    @ColumnInfo(name = "isPopupPassed")
    val isPopupPassed: Boolean,
    @ColumnInfo(name = "isFavoriteTabOpen")
    val isFavoriteTabOpen: Boolean
)