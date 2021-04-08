package com.bogdan.motivation

import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import java.sql.Time
import java.text.DateFormat
import java.util.*
import kotlin.system.exitProcess

class NotificationSettingsActivity : AppCompatActivity(),  TimePickerDialog.OnTimeSetListener{

    var dbHelper: DBHelper? = null

    var tvExplanationNotif: TextView? = null
    var horizontalLayout1: LinearLayout? = null
    var horizontalLayout2: LinearLayout? = null
    var horizontalLayout3: LinearLayout? = null
    var btnContinue: Button? = null

    var tvQuantityDefault: TextView? = null

    var tvExplanationAnimation: Animation? = null
    var horizontalLayout1Animation: Animation? = null
    var horizontalLayout2Animation: Animation? = null
    var horizontalLayout3Animation: Animation? = null
    var btnContinueAnimation: Animation? = null

    var startTime: TextView? = null
    var endTime: TextView? = null

    var hour = 0
    var minute = 0

    var savedHour = 0
    var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_settings)

        tvExplanationNotif = findViewById(R.id.textNotifExplanations)
        horizontalLayout1 = findViewById(R.id.horizontalLayout1)
        horizontalLayout2 = findViewById(R.id.horizontalLayout2)
        horizontalLayout3 = findViewById(R.id.horizontalLayout3)
        btnContinue = findViewById(R.id.btnContinue)

        tvQuantityDefault = findViewById(R.id.notif_quantity)
        startTime = findViewById(R.id.start_time)
        endTime = findViewById(R.id.end_time)

        tvExplanationAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim)
        horizontalLayout1Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim_2)
        horizontalLayout2Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim_2)
        horizontalLayout3Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim_2)
        btnContinueAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_anim_2)

        tvExplanationAnimation?.startOffset = 750
        horizontalLayout1Animation?.startOffset = 1500
        horizontalLayout2Animation?.startOffset = 1750
        horizontalLayout3Animation?.startOffset = 2000
        btnContinueAnimation?.startOffset = 2250

        tvExplanationNotif?.startAnimation(tvExplanationAnimation)
        horizontalLayout1?.startAnimation(horizontalLayout1Animation)
        horizontalLayout2?.startAnimation(horizontalLayout2Animation)
        horizontalLayout3?.startAnimation(horizontalLayout3Animation)
        btnContinue?.startAnimation(btnContinueAnimation)

        pickStartTime()
        pickEndTime()
    }


    fun onBtnMinusClicked(view: View){
        var number = tvQuantityDefault?.text.toString().substringBefore("X").toInt()
        if (number > 0)
        number --
        else number = 0
        tvQuantityDefault?.text = number.toString() + "X"
    }

    fun onBtnPlusClicked(view: View){
        var number = tvQuantityDefault?.text.toString().substringBefore("X").toInt()
        if (number <30)
            number ++
        else number = 30
        tvQuantityDefault?.text = number.toString() + "X"
    }

    private fun getTimeCalendar(){
        val calendar : Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    var listener: Int = 0

    private fun pickStartTime(){
        startTime?.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(this, R.style.TimePickerTheme,this, hour, minute, true).show()
            listener = 1
        }
    }

    private fun pickEndTime(){
        endTime?.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(this, R.style.TimePickerTheme, this, hour, minute, true).show()
            listener = 2
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var editHour = hourOfDay.toString()
        var editMinute = minute.toString()
        if (hourOfDay.toString().length == 1)
            editHour = "0$hourOfDay"
        if (minute.toString().length == 1)
            editMinute = "0$minute"
        savedHour = hourOfDay
        savedMinute = minute
        if (listener == 1) {
            startTime?.text = editHour + ":" + editMinute
        }else if (listener == 2){
            endTime?.text = editHour + ":" + editMinute
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

    fun onBntContinueClicked(view: View){
        dbHelper = DBHelper(applicationContext)
        val database: SQLiteDatabase = dbHelper!!.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_ID, 1)
        contentValues.put(KEY_QUANTITY, tvQuantityDefault!!.text.toString().substringBefore("X"))
        contentValues.put(KEY_START_TIME, startTime!!.text.toString().substring(0,2))
        contentValues.put(KEY_END_TIME, endTime!!.text.toString().substring(0,2))

        database.insertWithOnConflict(
            TABLE_NOTIFICATION,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_IGNORE
        )

        startActivity(Intent(applicationContext, ThemePickerActivity::class.java))
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
    }


}