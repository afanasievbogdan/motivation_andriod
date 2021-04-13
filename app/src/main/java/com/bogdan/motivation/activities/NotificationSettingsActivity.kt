package com.bogdan.motivation.activities

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.ActivityNotificationSettingsBinding
import com.bogdan.motivation.db.DBManager
import java.util.*
import kotlin.system.exitProcess

class NotificationSettingsActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityNotificationSettingsBinding
    private val dbManager = DBManager(applicationContext)

    private var hour = 0
    private var minute = 0
    private var savedHour = 0
    private var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dbManager.openDb()

        onClickBtnPlus()
        onClickBtnMinus()
        onClickBntContinue()
        setUiAnimations()

        pickStartTime()
        pickEndTime()
    }

    private fun setUiAnimations() {
        val tvExplanationAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim
        )
        val horizontalLayout1Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim_2
        )
        val horizontalLayout2Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim_2
        )
        val horizontalLayout3Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim_2
        )
        val btnContinueAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_anim_2
        )

        tvExplanationAnimation.startOffset = 750
        horizontalLayout1Animation.startOffset = 1500
        horizontalLayout2Animation.startOffset = 1750
        horizontalLayout3Animation.startOffset = 2000
        btnContinueAnimation.startOffset = 2250

        binding.textNotifExplanations.startAnimation(tvExplanationAnimation)
        binding.horizontalLayout1.startAnimation(horizontalLayout1Animation)
        binding.horizontalLayout2.startAnimation(horizontalLayout2Animation)
        binding.horizontalLayout3.startAnimation(horizontalLayout3Animation)
        binding.btnContinue.startAnimation(btnContinueAnimation)
    }

    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            var number = binding.notifQuantity.text.toString().substringBefore("X").toInt()
            if (number > 0)
                number--
            else number = 0
            binding.notifQuantity.text = "${number}X"
        }
    }

    private fun onClickBtnPlus() {
        binding.btnPlus.setOnClickListener {
            var number = binding.notifQuantity.text.toString().substringBefore("X").toInt()
            if (number < 30)
                number++
            else number = 30
            binding.notifQuantity.text = "${number}X"
        }
    }

    private fun getTimeCalendar() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    //TODO ?)
    private var listener: Int = 0

    private fun pickStartTime() {
        binding.startTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                this,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            listener = 1
        }
    }

    private fun pickEndTime() {
        binding.endTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                this,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            listener = 2
        }
    }

    //TODO эту функцию нужно рефакторить
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
            binding.startTime.text = "$editHour : $editMinute"
        } else if (listener == 2) {
            binding.endTime.text = "$editHour : $editMinute"
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        exitProcess(0)
    }

    private fun onClickBntContinue() {

        binding.btnContinue.setOnClickListener {
            val quantity = binding.notifQuantity.text.toString().substringBefore("X")
            val startTime = binding.startTime.text.toString().substring(0, 2)
            val endTime = binding.endTime.text.toString().substring(0, 2)

            dbManager.insetToNotificationsDb(quantity, startTime, endTime)

            startActivity(Intent(applicationContext, ThemePickerActivity::class.java))
            overridePendingTransition(
                R.anim.slide_in,
                R.anim.slide_out
            )
        }
    }


}