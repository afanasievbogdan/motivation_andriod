package com.bogdan.motivation.ui.fragments.notificationsettings

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentNotificationSettingsBinding
import com.bogdan.motivation.helpers.playAnimationWithOffset
import java.util.*

class NotificationSettingsFragment :
    Fragment(R.layout.fragment_notification_settings),
    OnTimeSetListener {

    private var _binding: FragmentNotificationSettingsBinding? = null
    private val binding get() = _binding!!

    private val notificationSettingsViewModel: NotificationSettingsViewModel by viewModels()

    private var isStartTimer = true
    private var notificationQuantity = 10
    private var hour = 0
    private var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationSettingsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAnimations()
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

    private fun setAnimations() {
        with(binding) {
            tvNotificationExplanations.playAnimationWithOffset(
                animResId = R.anim.anim_fade_slow,
                750
            )
            containerNotificationQuantity.playAnimationWithOffset(
                animResId = R.anim.anim_fade_fast,
                1500
            )
            containerStartTime.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 1750)
            containerEndTime.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 2000)
            btnContinue.playAnimationWithOffset(animResId = R.anim.anim_fade_fast, 2250)
        }
    }

    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            if (notificationQuantity > 1) notificationQuantity--
            binding.tvNotificationsQuantity.text = resources.getString(
                R.string.notifications_quantity_changed, notificationQuantity
            )
        }
    }

    private fun onClickBtnPlus() {
        binding.btnPlus.setOnClickListener {
            if (notificationQuantity < 30) notificationQuantity++
            binding.tvNotificationsQuantity.text = resources.getString(
                R.string.notifications_quantity_changed, notificationQuantity
            )
        }
    }

    private fun getTimeCalendar() {
        Calendar.getInstance().apply {
            hour = get(Calendar.HOUR)
            minute = get(Calendar.MINUTE)
        }
    }

    private fun pickStartTime() {
        binding.btnStartTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                activity,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            isStartTimer = true
        }
    }

    private fun pickEndTime() {
        binding.btnEndTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                activity,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
            isStartTimer = false
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val editHour =
            if (hourOfDay.toString().length == 1) "0$hourOfDay"
            else "$hourOfDay"
        val editMinute =
            if (minute.toString().length == 1) "0$minute"
            else "$minute"

        if (isStartTimer) {
            binding.btnStartTime.text = resources.getString(
                R.string.notifications_btn_changed_time, editHour, editMinute
            )
        } else {
            binding.btnEndTime.text = resources.getString(
                R.string.notifications_btn_changed_time, editHour, editMinute
            )
        }
    }

    private fun onClickBntContinue() {
        with(binding) {
            btnContinue.setOnClickListener {
                val quantity = tvNotificationsQuantity.text.toString().substringBefore("X")
                val startTime = btnStartTime.text.toString().substring(0, 2)
                val endTime = btnEndTime.text.toString().substring(0, 2)
                notificationSettingsViewModel.insetToNotificationsDb(quantity, startTime, endTime)
                findNavController().navigate(
                    NotificationSettingsFragmentDirections.actionNotificationSettingsFragmentToThemePickerFragment()
                )
            }
        }
    }
}