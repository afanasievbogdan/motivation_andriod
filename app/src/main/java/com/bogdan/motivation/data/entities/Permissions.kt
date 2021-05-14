package com.bogdan.motivation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Permissions")
data class Permissions(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "_settingsPassed")
    val isSettingsPassed: Boolean,
    @ColumnInfo(name = "_popupPassed")
    val isPopupPassed: Boolean,
    @ColumnInfo(name = "_favoriteTabOpen")
    val isFavoriteTabOpen: Boolean
)