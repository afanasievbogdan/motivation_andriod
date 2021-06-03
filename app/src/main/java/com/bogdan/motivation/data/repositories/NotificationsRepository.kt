package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Notification

class NotificationsRepository {

    lateinit var db: ApplicationDatabase

    suspend fun insertNotification(notification: Notification) = db.notificationDao().insertNotification(notification)

    suspend fun getNotification(): Notification = db.notificationDao().getNotification()

    fun getStartTime(): String = db.notificationDao().getStartTime()

    fun getEndTime(): String = db.notificationDao().getEndTime()
}