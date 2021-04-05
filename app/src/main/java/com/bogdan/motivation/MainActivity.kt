package com.bogdan.motivation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import java.io.IOException
import java.util.*

private lateinit var prefs: SharedPreferences


class MainActivity : AppCompatActivity() {

    var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(applicationContext)

        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_ID, 1)
        contentValues.put(KEY_SETTING_COMPLETE, "0")
        contentValues.put(KEY_POPUP_COMPLETE, "0")

        database.insertWithOnConflict(
            TABLE_PERMISSIONS,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_IGNORE
        )

        val cursor: Cursor = database.rawQuery(
            "SELECT $KEY_SETTING_COMPLETE FROM $TABLE_PERMISSIONS WHERE $KEY_ID = 1", null
        )

        var isPermitted = "0"
        if (cursor.moveToFirst()) {
            isPermitted = cursor.getString(0)
        }

        cursor.close()
        database.close()

        if (isPermitted == "0") {
            startActivity(Intent(applicationContext, HelloActivity::class.java))
        } else if (isPermitted == "1") {
            getQuoteFromApi()
            val helloActivity = Intent(applicationContext, MotivationActivity::class.java)
            android.os.Handler().postDelayed({ startActivity(helloActivity) }, 2000)
        }
    }

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

                val database: SQLiteDatabase = dbHelper!!.writableDatabase
                //database.delete(TABLE_ALL_QUOTES, null, null)
                val contentValues = ContentValues()

                root.forEach {
                    contentValues.put(KEY_QUOTE, it.quote)
                    contentValues.put(KEY_AUTHOR, it.author)
                    contentValues.put(KEY_FAVORITE, "0")

                    database.insertWithOnConflict(
                        TABLE_ALL_QUOTES,
                        null,
                        contentValues,
                        SQLiteDatabase.CONFLICT_IGNORE
                    )
                }
            }
        })
    }

    class Root(
        var id: Int,
        var quote: String,
        var author: String,
        var created_at: Date,
        var updated_at: Date
    )

}
