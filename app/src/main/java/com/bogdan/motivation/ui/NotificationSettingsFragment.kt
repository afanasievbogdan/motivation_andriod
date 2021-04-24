package com.bogdan.motivation.ui

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentNotificationSettingsBinding
import com.bogdan.motivation.db.DBManager
import java.util.*

@SuppressLint("SetTextI18n")
class NotificationSettingsFragment : Fragment(R.layout.fragment_notification_settings),
    OnTimeSetListener {

    private var _binding: FragmentNotificationSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager

    private var notificationQuantity = 10
    private var hour = 0
    private var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentNotificationSettingsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiAnimations()
        onClickBtnMinus()
        onClickBtnPlus()
        pickStartTime()
        pickEndTime()
        onClickBntContinue()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setUiAnimations() {
        val tvNotificationExplanationsAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val notificationQuantityContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val startTimeContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val endTimeContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val btnContinueAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )

        tvNotificationExplanationsAnimation.startOffset = 750
        notificationQuantityContainerAnimation.startOffset = 1500
        startTimeContainerAnimation.startOffset = 1750
        endTimeContainerAnimation.startOffset = 2000
        btnContinueAnimation.startOffset = 2250

        with(binding) {
            tvNotificationExplanations.startAnimation(tvNotificationExplanationsAnimation)
            notificationQuantityContainer.startAnimation(notificationQuantityContainerAnimation)
            startTimeContainer.startAnimation(startTimeContainerAnimation)
            endTimeContainer.startAnimation(endTimeContainerAnimation)
            btnContinue.startAnimation(btnContinueAnimation)
        }

    }

    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            if (notificationQuantity > 0) notificationQuantity--
            else notificationQuantity = 0
            binding.notificationsQuantity.text = "${notificationQuantity}X"
        }
    }

    private fun onClickBtnPlus() {
        binding.btnPlus.setOnClickListener {
            if (notificationQuantity < 30) notificationQuantity++
            else notificationQuantity = 30
            binding.notificationsQuantity.text = "${notificationQuantity}X"
        }
    }

    private fun getTimeCalendar() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private var isStartTimer = true

    private fun pickStartTime() {
        binding.startTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                activity,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            isStartTimer = true
        }
    }

    private fun pickEndTime() {
        binding.endTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                activity,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            isStartTimer = false
        }
    }

    //TODO эту функцию нужно рефакторить
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        var editHour = hourOfDay.toString()
        var editMinute = minute.toString()
        if (editHour.length == 1)
            editHour = "0$hourOfDay"
        if (editMinute.length == 1)
            editMinute = "0$minute"

        if (isStartTimer) {
            binding.startTime.text = "$editHour : $editMinute"
        } else {
            binding.endTime.text = "$editHour : $editMinute"
        }
    }

    private fun onClickBntContinue() {
        with(binding) {
            btnContinue.setOnClickListener {
                val quantity = notificationsQuantity.text.toString().substringBefore("X")
                val startTime = startTime.text.toString().substring(0, 2)
                val endTime = endTime.text.toString().substring(0, 2)
                dbManager.insetToNotificationsDb(quantity, startTime, endTime)
                findNavController().navigate(
                    NotificationSettingsFragmentDirections.actionNotificationSettingsFragmentToThemePickerFragment())
            }
        }
    }
}