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
    // TODO Почему не юзаешь название табл. как константу
    @Query("SELECT * FROM Notifications WHERE id = 1")
    suspend fun getNotification(): Notification

    @Query("SELECT startTime FROM Notifications WHERE id = 1")
    suspend fun getStartTime(): String

    @Query("SELECT endTime FROM Notifications WHERE id = 1")
    suspend fun getEndTime(): String
}