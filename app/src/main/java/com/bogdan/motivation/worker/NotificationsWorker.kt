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
import com.bogdan.motivation.helpers.Constants
import java.text.SimpleDateFormat
import java.util.*

class NotificationsWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    // TODO: 15.05.2021 В константы

    override fun doWork(): Result {
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
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.channelId, Constants.name, importance).apply {
                description = Constants.descriptionText
            }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // TODO: 15.05.2021 apply для билдера
    private fun sendNotification() {
        val notificationsText = RepositoryProvider.quotesRepository.getRandomQuote()
        val builder = NotificationCompat.Builder(applicationContext, Constants.channelId)
        builder.apply {
            setContentTitle("Daily Motivation")
            setContentText(notificationsText.quote)
            setStyle(NotificationCompat.BigTextStyle())
            setSmallIcon(R.mipmap.ic_launcher_round)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        NotificationManagerCompat
            .from(applicationContext)
            .notify(Constants.notificationId, builder.build())
    }

    // TODO: 15.05.2021 дырявая функция
    private fun isCorrectTime(): Boolean {
        val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())
        val currentHour = simpleDateFormat.format(Date())
        val start = RepositoryProvider.notificationsRepository.getStartTime()
        val end = RepositoryProvider.notificationsRepository.getEndTime()
        return currentHour.toInt() >= start.toInt() && currentHour.toInt() < end.toInt()
    }
}
