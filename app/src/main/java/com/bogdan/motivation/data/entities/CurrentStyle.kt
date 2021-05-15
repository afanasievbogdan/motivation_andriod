package com.bogdan.motivation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: 15.05.2021 сделай remote and local entities
@Entity(tableName = "Styles")
data class CurrentStyle(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    // TODO: 15.05.2021 почему c _style?
    @ColumnInfo(name = "_style")
    val currentStyle: Styles
)