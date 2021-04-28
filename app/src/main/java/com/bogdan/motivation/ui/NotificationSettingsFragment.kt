package com.bogdan.motivation.ui

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentNotificationSettingsBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.playAnimation
import java.util.Calendar

class NotificationSettingsFragment :
    Fragment(R.layout.fragment_notification_settings),
    OnTimeSetListener {

    private var _binding: FragmentNotificationSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbManager: DBManager

    private var isStartTimer = true
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

    // TODO: замени это на экстеншен для вьюхи, тут должно быть 5 строк + засэтить это для каждой вью ✓ DONE
    private fun setUiAnimations() {
        with(binding) {
            tvNotificationExplanations.playAnimation(animResId = R.anim.anim_fade_slow, 750)
            containerNotificationQuantity.playAnimation(animResId = R.anim.anim_fade_fast, 1500)
            containerStartTime.playAnimation(animResId = R.anim.anim_fade_fast, 1750)
            containerEndTime.playAnimation(animResId = R.anim.anim_fade_fast, 2000)
            btnContinue.playAnimation(animResId = R.anim.anim_fade_fast, 2250)
        }
    }

    // TODO: Вместо сапресса вынеси в ресурсы ✓ DONE
    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            // TODO: зачем тут else? ✓ DONE
            if (notificationQuantity > 0) notificationQuantity--
            binding.tvNotificationsQuantity.text = resources.getString(
                R.string.notifications_quantity_changed, notificationQuantity
            )
        }
    }

    // TODO: Вместо сапресса вынеси в ресурсы ✓ DONE
    private fun onClickBtnPlus() {
        binding.btnPlus.setOnClickListener {
            // TODO: зачем тут else? ✓ DONE
            if (notificationQuantity < 30) notificationQuantity++
            binding.tvNotificationsQuantity.text = resources.getString(
                R.string.notifications_quantity_changed, notificationQuantity
            )
        }
    }

    private fun getTimeCalendar() {
        // TODO: apply ✓ DONE
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
                dbManager.insetToNotificationsDb(quantity, startTime, endTime)
                findNavController().navigate(
                    NotificationSettingsFragmentDirections.actionNotificationSettingsFragmentToThemePickerFragment()
                )
            }
        }
    }
}