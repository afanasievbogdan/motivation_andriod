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
import com.bogdan.motivation.db.DBManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val channelId = "channel_id_01"
    private val notificationId = 101
    // TODO: зачем ещё раз обьявлять переменную? добавь private val B constructor ✓ DONE

    private val dbManager = DBManager(appContext)

    // TODO: добаBь {} для else ✓ DONE
    override fun doWork(): Result {
        dbManager.openDb()
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

    private fun sendNotification() {
        val notificationsText = dbManager.readRandomQuoteFromQuotesDb()
        val builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Daily Motivation")
            .setContentText(notificationsText)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .priority = NotificationCompat.PRIORITY_DEFAULT
        // TODO: with зачем? ✓ DONE
        NotificationManagerCompat.from(applicationContext)
            .notify(notificationId, builder.build())
    }

    // TODO: название переменной? почему саппресс? ✓ DONE
    private fun isCorrectTime(): Boolean {
        val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())

        val currentHour = simpleDateFormat.format(Date())

        val start = dbManager.readStartTimeFromNotificationsDb()
        val end = dbManager.readEndTimeFromNotificationsDb()

        return currentHour.toInt() >= start.toInt() && currentHour.toInt() < end.toInt()
    }
}
