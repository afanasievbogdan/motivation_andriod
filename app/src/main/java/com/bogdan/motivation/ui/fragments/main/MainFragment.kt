package com.bogdan.motivation.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bogdan.motivation.R
import com.bogdan.motivation.databinding.FragmentMainBinding
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.repositories.RepositoryProvider
import com.bogdan.motivation.worker.NotificationsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val getQuotesScope = CoroutineScope(Dispatchers.IO)

    private lateinit var db: DBManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = RepositoryProvider.dbRepository.getDbInstance()
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
        val isSettingsPassed = db.readSettingsFromPermissionsDb()
        if (isSettingsPassed == "0") {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHelloFragment()
            )
        } else if (isSettingsPassed == "1") {
            getQuotesScope.launch {
                RepositoryProvider.quotesRepository.getQuotesFromApi(db)
            }
            setNotificationWorker()
            val isPopupPassed = db.readPopupFromPermissionsDb()
            if (isPopupPassed == "0") {
                lifecycleScope.launch {
                    delay(2000L)
                    openActivity()
                }
            } else {
                openActivity()
            }
        }
    }

    private fun openActivity() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMotivationFragment(
                "General"
            )
        )
    }

    private fun setNotificationWorker() {
        val workTag = "WORK_TAG"

        val notifQuantity = db.readQuantityFromNotificationsDb()
        val startTime = db.readStartTimeFromNotificationsDb()
        val endTime = db.readEndTimeFromNotificationsDb()

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