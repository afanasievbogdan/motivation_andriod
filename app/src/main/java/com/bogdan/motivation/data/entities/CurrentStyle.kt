package com.bogdan.motivation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Styles")
data class CurrentStyle(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "_style")
    val currentStyle: Styles
)