package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.local.Notification

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM Notifications WHERE id = 1")
    suspend fun getNotification(): Notification

    // TODO: 15.05.2021 почему не suspend?
    @Query("SELECT startTime FROM Notifications WHERE id = 1")
    fun getStartTime(): String

    @Query("SELECT endTime FROM Notifications WHERE id = 1")
    fun getEndTime(): String
}