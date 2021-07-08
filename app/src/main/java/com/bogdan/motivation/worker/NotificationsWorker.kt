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
import com.bogdan.motivation.data.repositories.NotificationsRepository
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.helpers.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NotificationsWorker @Inject constructor(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val notificationsRepository: NotificationsRepository,
    private val quotesRepository: QuotesRepository,
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        isCorrectTime()
        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.channelId, Constants.name, importance).apply {
                description = Constants.descriptionText
            }
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val notificationsText = quotesRepository.getRandomQuote()
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
    }

    private fun isCorrectTime() {
        CoroutineScope(Dispatchers.IO).launch {
            val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())
            val currentHour = simpleDateFormat.format(Date())
            val start = notificationsRepository.getStartTime()
            val end = notificationsRepository.getEndTime()
            val isCorrectTime = currentHour.toInt() >= start.toInt() && currentHour.toInt() < end.toInt()
            if (isCorrectTime) {
                createNotificationChannel()
                sendNotification()
                Log.i("MyINFO", "Im logging with notify!!!")
            } else {
                Log.i("MyINFO", "Im logging but with no notify :C")
            }
        }
    }
}