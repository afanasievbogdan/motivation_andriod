package com.bogdan.motivation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bogdan.motivation.worker.NotificationsWorker
import com.bogdan.motivation.R
import com.bogdan.motivation.entities.Root
import com.bogdan.motivation.db.DBManager
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private val dbManager = DBManager(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager.openDb()

        dbManager.insetToPermissionsDb("0", "0")
        chooseActivityToOpen()
    }

    private fun chooseActivityToOpen() {
        val isSettingsPassed = dbManager.readSettingsFromPermissionsDb()

        if (isSettingsPassed == "0") {
            startActivity(Intent(applicationContext, HelloActivity::class.java))
        } else if (isSettingsPassed == "1") {
            getQuoteFromApi()
            setNotificationWorker()

            val helloActivity = Intent(applicationContext, MotivationActivity::class.java)
            Timer().schedule(2000) { startActivity(helloActivity) }
        }
    }

    private fun setNotificationWorker() {
        val notifQuantity = dbManager.readQuantityFromNotificationsDb()
        val startTime = dbManager.readStartTimeFromNotificationsDb()
        val endTime = dbManager.readEndTimeFromNotificationsDb()

        var repeatInterval = (endTime.toInt() - startTime.toInt()) * 60 / notifQuantity.toInt()
        if (repeatInterval < 16)
            repeatInterval = 16

        val myConstraints: Constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            NotificationsWorker::class.java,
            repeatInterval.toLong(), TimeUnit.MINUTES,
            repeatInterval.toLong() - 1, TimeUnit.MINUTES
        )
            .setConstraints(myConstraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "WORK_TAG",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    // TODO разделить на функции, реквест должен быть переиспользуемым и настройка okhttp должна быть в отдельном классе - object
    // TODO так же рекомендуем использовать moshi or kotlinx serialization в качестве сериализатора
    private fun getQuoteFromApi() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://31.42.190.127/api/quotes")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()?.string()

                val mapper = jacksonObjectMapper()
                val root = mapper.readValue<List<Root>>(jsonString!!)

                root.forEach {
                    dbManager.insetToQuotesDb(it.quote, it.author, "0")
                }
            }
        })
    }
}
