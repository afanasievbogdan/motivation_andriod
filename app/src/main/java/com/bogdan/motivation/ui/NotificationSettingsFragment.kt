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

    private var hour = 0
    private var minute = 0
    private var savedHour = 0
    private var savedMinute = 0

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
    //todo обрати тут внимание на enter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiAnimations()

        onClickBtnMinus()
        onClickBtnPlus()
        pickStartTime()
        pickEndTime()

        onClickBntContinue()
    }
//todo чисел в названиях быть не должно
    private fun setUiAnimations() {
        val tvExplanationAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim
        )
        val horizontalLayout1Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val horizontalLayout2Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val horizontalLayout3Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )
        val btnContinueAnimation = AnimationUtils.loadAnimation(
            context,
            R.anim.fade_anim_2
        )

        tvExplanationAnimation.startOffset = 750
        horizontalLayout1Animation.startOffset = 1500
        horizontalLayout2Animation.startOffset = 1750
        horizontalLayout3Animation.startOffset = 2000
        btnContinueAnimation.startOffset = 2250

        binding.textNotificationExplanations.startAnimation(tvExplanationAnimation)
        binding.horizontalLayout1.startAnimation(horizontalLayout1Animation)
        binding.horizontalLayout2.startAnimation(horizontalLayout2Animation)
        binding.horizontalLayout3.startAnimation(horizontalLayout3Animation)
        binding.btnContinue.startAnimation(btnContinueAnimation)
    }

    //todo тут нужно работать с переменной, а не доставать и обрезать значение из вью
    // if а в одну строку либо с {}
    private fun onClickBtnMinus() {
        binding.btnMinus.setOnClickListener {
            var number = binding.notifQuantity.text.toString().substringBefore("X").toInt()
            if (number > 0)
                number--
            else number = 0
            binding.notifQuantity.text = "${number}X"
        }
    }
//todo тоже самое
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
    private var isStartTimer = true

    private fun pickStartTime() {
        binding.startTime.setOnClickListener {
            getTimeCalendar()

            TimePickerDialog(
                activity,
                R.style.TimePickerTheme, this, hour, minute, true
            ).show()
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
        if (hourOfDay.toString().length == 1)
            editHour = "0$hourOfDay"
        if (minute.toString().length == 1)
            editMinute = "0$minute"
        savedHour = hourOfDay
        savedMinute = minute
        if (isStartTimer) {
            binding.startTime.text = "$editHour : $editMinute"
        } else if (!isStartTimer) {
            binding.endTime.text = "$editHour : $editMinute"
        }
    }

    private fun onClickBntContinue() {
//todo with(binding)
        binding.btnContinue.setOnClickListener {
            val quantity = binding.notifQuantity.text.toString().substringBefore("X")
            val startTime = binding.startTime.text.toString().substring(0, 2)
            val endTime = binding.endTime.text.toString().substring(0, 2)

            dbManager.insetToNotificationsDb(quantity, startTime, endTime)

            val action =
                NotificationSettingsFragmentDirections.actionNotificationSettingsFragmentToThemePickerFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}