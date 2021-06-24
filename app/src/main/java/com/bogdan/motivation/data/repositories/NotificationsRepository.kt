package com.bogdan.motivation.data.repositories

import com.bogdan.motivation.data.dao.NotificationDao
import com.bogdan.motivation.data.entities.local.Notification
import javax.inject.Inject

class NotificationsRepository @Inject constructor(private val notificationDao: NotificationDao) {

    suspend fun insertNotification(notification: Notification) = notificationDao.insertNotification(notification)

    suspend fun getNotification(): Notification = notificationDao.getNotification()

    suspend fun getStartTime(): String = notificationDao.getStartTime()

    suspend fun getEndTime(): String = notificationDao.getEndTime()
}