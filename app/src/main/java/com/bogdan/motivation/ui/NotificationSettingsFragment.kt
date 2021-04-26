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
import java.util.Calendar

@SuppressLint("SetTextI18n")
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
    // TODO: замени это на экстеншен для вьюхи, тут должно быть 5 строк + засэтить это для каждой вью
    private fun setUiAnimations() {
        val tvNotificationExplanationsAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_slow
        )

        val notificationQuantityContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_fast
        )

        val startTimeContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_fast
        )

        val endTimeContainerAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_fast
        )

        val btnContinueAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.animation_fade_fast
        )

        tvNotificationExplanationsAnimation.startOffset = 750
        notificationQuantityContainerAnimation.startOffset = 1500
        startTimeContainerAnimation.startOffset = 1750
        endTimeContainerAnimation.startOffset = 2000
        btnContinueAnimation.startOffset = 2250

        with(binding) {
            tvNotificationExplanations.startAnimation(tvNotificationExplanationsAnimation)
            containerNotificationQuantity.startAnimation(notificationQuantityContainerAnimation)
            containerStartTime.startAnimation(startTimeContainerAnimation)
            containerEndTime.startAnimation(endTimeContainerAnimation)
            btnContinue.startAnimation(btnContinueAnimation)
        }
    }
    // TODO: Вместо сапресса вынеси в ресурсы
    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            // TODO: зачем тут else?
            if (notificationQuantity > 0) notificationQuantity--
            else notificationQuantity = 0
            binding.tvNotificationsQuantity.text = "${notificationQuantity}X"
        }
    }
    // TODO: Вместо сапресса вынеси в ресурсы
    private fun onClickBtnPlus() {
        binding.btnPlus.setOnClickListener {
            // TODO: зачем тут else?
            if (notificationQuantity < 30) notificationQuantity++
            else notificationQuantity = 30
            binding.tvNotificationsQuantity.text = "${notificationQuantity}X"
        }
    }

    private fun getTimeCalendar() {
        // TODO: apply
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
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
            binding.btnStartTime.text = "$editHour : $editMinute"
        } else {
            binding.btnEndTime.text = "$editHour : $editMinute"
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