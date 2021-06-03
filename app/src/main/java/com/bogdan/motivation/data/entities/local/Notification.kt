package com.bogdan.motivation.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan.motivation.helpers.Constants

@Entity(tableName = Constants.TABLE_NOTIFICATIONS)
data class Notification(
    @PrimaryKey
    val id: Long = 1,
    @ColumnInfo(name = "quantity")
    val quantity: String,
    @ColumnInfo(name = "startTime")
    val startTime: String,
    @ColumnInfo(name = "endTime")
    val endTime: String
)