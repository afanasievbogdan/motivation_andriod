package com.bogdan.motivation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "_quantity")
    val quantity: String,
    @ColumnInfo(name = "_startTime")
    val startTime: String,
    @ColumnInfo(name = "_endTime")
    val endTime: String
)