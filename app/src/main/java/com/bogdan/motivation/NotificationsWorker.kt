package com.bogdan.motivation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class NotificationsWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    private val channelId = "channel_id_01"
    private val notificationId = 101

    private val mContext = appContext

    private var dbHelper: DBHelper? = DBHelper(applicationContext)

    private var notificationsText: String? = null

    override fun doWork(): Result {

        if (isCorrectTime()){
            createNotificationChannel()
            sendNotification()
            Log.i("MyINFO", "Im logging with notify!!!")
        }else
            Log.i("MyINFO", "Im logging but with no notify :C")

        return Result.success()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        getRandomQuoteFromDb()
        val builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("Daily Motivation")
            .setContentText(notificationsText)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .priority = NotificationCompat.PRIORITY_DEFAULT

        with(NotificationManagerCompat.from(applicationContext)){
            notify(notificationId, builder.build())
        }
    }

    private fun getRandomQuoteFromDb(){
        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val cursor: Cursor = database.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_ALL_QUOTES", null
        )

        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()

        val mRandom = Random.nextInt(count)

        val cursor1: Cursor = database.rawQuery(
            "SELECT $KEY_QUOTE, $KEY_AUTHOR FROM $TABLE_ALL_QUOTES WHERE $KEY_ID = $mRandom", null
        )

        if (cursor1.moveToFirst()) {
            val quote = cursor1.getString(0)
            val author = cursor1.getString(1)
            notificationsText = "$quote $author"
        }

        cursor1.close()
    }

    private fun isCorrectTime() : Boolean{
        val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
        val currentDate = sdf.format(Date())
        val currentHour = currentDate.substring(10,12)

        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val cursor: Cursor = database.rawQuery(
            "SELECT $KEY_START_TIME, $KEY_END_TIME FROM $TABLE_NOTIFICATION", null
        )

        var start = "0"
        var end = "0"
        if (cursor.moveToFirst()) {
            start = cursor.getString(0)
            end = cursor.getString(1)
        }
        cursor.close()

        return currentHour.toInt() >= start.toInt() && currentHour.toInt() < end.toInt()
    }

}