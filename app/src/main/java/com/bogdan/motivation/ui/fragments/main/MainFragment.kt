package com.bogdan.motivation.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bogdan.motivation.R
import com.bogdan.motivation.data.entities.local.Notification
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentMainBinding
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.worker.NotificationsWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // TODO: 15.05.2021 переделай обсервер, почему функция называется All?)
    private fun initializeObserver() {
        mainViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is Utils -> {
                        Log.i("Check", it.data.toString())
                        chooseActivityToOpen(it.data)
                    }
                    is Notification -> setNotificationWorker(it.data)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    private fun chooseActivityToOpen(utils: Utils) {
        if (utils.isSettingsPassed) {
            mainViewModel.getQuotesFromApi()
            openMotivationFragment(utils.isPopupPassed)
        } else {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHelloFragment()
            )
        }
    }

    // TODO: 15.05.2021 переименуй на openMotivationFragment например, а нижнюю удали
    private fun openMotivationFragment(isPopupPassed: Boolean) {
        if (isPopupPassed) {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToMotivationFragment(
                    "General"
                )
            )
        } else {
            lifecycleScope.launch {
                delay(2000L)
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToMotivationFragment(
                        "General"
                    )
                )
            }
        }
    }

    // TODO: 15.05.2021 tag в константы
    private fun setNotificationWorker(notification: Notification) {
        val quantity = notification.quantity
        val start = notification.startTime
        val end = notification.endTime

        var repeatInterval = (end.toInt() - start.toInt()) * 60 / quantity.toInt()
        repeatInterval = repeatInterval.coerceAtLeast(16)

        val workRequest = PeriodicWorkRequest.Builder(
            NotificationsWorker::class.java,
            repeatInterval.toLong(), TimeUnit.MINUTES,
            repeatInterval.toLong() - 1, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            Constants.workTag,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}