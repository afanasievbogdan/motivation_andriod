package com.bogdan.motivation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bogdan.motivation.R
import com.bogdan.motivation.api.RetrofitConfiguration
import com.bogdan.motivation.databinding.FragmentMainBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.worker.NotificationsWorker
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbManager: DBManager
    // todo инициализируй бд как синглтон в мейн активити
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dbManager = DBManager(requireContext())
        dbManager.openDb()
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseActivityToOpen()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun chooseActivityToOpen() {
        val isSettingsPassed = dbManager.readSettingsFromPermissionsDb()
        if (isSettingsPassed == "0") {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHelloFragment()
            )
        } else if (isSettingsPassed == "1") {
            RetrofitConfiguration.getQuotesFromApi(dbManager)
            setNotificationWorker()
            Timer().schedule(2000) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMotivationFragment()
                )
            }
        }
    }

    private fun setNotificationWorker() {
        val workTag = "WORK_TAG"

        val notifQuantity = dbManager.readQuantityFromNotificationsDb()
        val startTime = dbManager.readStartTimeFromNotificationsDb()
        val endTime = dbManager.readEndTimeFromNotificationsDb()

        var repeatInterval = (endTime.toInt() - startTime.toInt()) * 60 / notifQuantity.toInt()
        repeatInterval = repeatInterval.coerceAtLeast(16)

        val workRequest = PeriodicWorkRequest.Builder(
            NotificationsWorker::class.java,
            repeatInterval.toLong(), TimeUnit.MINUTES,
            repeatInterval.toLong() - 1, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            workTag,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}
