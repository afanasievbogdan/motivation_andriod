package com.bogdan.motivation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogdan.motivation.data.entities.Notification
import retrofit2.http.Query

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotification(notification: Notification)

    @Query("SELECT * FROM Notifications WHERE id = 1")
    suspend fun readNotification(): Notification

    // TODO: 15.05.2021 remove comments
    //    @Query("SELECT _quantity FROM Notifications WHERE id = 1")
//    suspend fun readQuantity(): String
//
    // TODO: 15.05.2021 почему не suspend?
    @Query("SELECT _startTime FROM Notifications WHERE id = 1")
    fun readStartTime(): String

    @Query("SELECT _endTime FROM Notifications WHERE id = 1")
    fun readEndTime(): String
}