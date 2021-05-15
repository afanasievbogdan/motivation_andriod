package com.bogdan.motivation.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bogdan.motivation.R
import com.bogdan.motivation.data.repositories.RepositoryProvider
import java.text.SimpleDateFormat
import java.util.*

class NotificationsWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    // TODO: 15.05.2021 В константы
    private val channelId = "channel_id_01"
    private val notificationId = 101

    private val db = RepositoryProvider.dbRepository

    override fun doWork(): Result {
        RepositoryProvider.dbRepository.connectToDb(applicationContext)
        if (isCorrectTime()) {
            createNotificationChannel()
            sendNotification()
            Log.i("MyINFO", "Im logging with notify!!!")
        } else {
            Log.i("MyINFO", "Im logging but with no notify :C")
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: 15.05.2021 В константы
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // TODO: 15.05.2021 apply для билдера
    private fun sendNotification() {
        val notificationsText = db.readRandomQuote()
        val builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Daily Motivation")
            .setContentText(notificationsText.quote)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .priority = NotificationCompat.PRIORITY_DEFAULT
        NotificationManagerCompat.from(applicationContext)
            .notify(notificationId, builder.build())
    }

    // TODO: 15.05.2021 дырявая функция
    private fun isCorrectTime(): Boolean {
        val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())

        val currentHour = simpleDateFormat.format(Date())

        val start = db.readStartTime()
        val end = db.readEndTime()

        return currentHour.toInt() >= start.toInt() && currentHour.toInt() < end.toInt()
    }
}
