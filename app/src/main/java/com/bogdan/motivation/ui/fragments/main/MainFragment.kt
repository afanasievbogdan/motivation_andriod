package com.bogdan.motivation.ui.fragments.main

import android.os.Bundle
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
import com.bogdan.motivation.data.entities.Notification
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.databinding.FragmentMainBinding
import com.bogdan.motivation.ui.State
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

        initializeAllObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initializeAllObserver() {
        mainViewModel.state.observe(
            viewLifecycleOwner,
            {
                if (it != null && it is State.SuccessState<*>) {
                    when (it.data) {
                        is Permissions -> chooseActivityToOpen(it.data)
                        is Notification -> setNotificationWorker(it.data)
                    }
                }
            }
        )
    }

    private fun chooseActivityToOpen(permissions: Permissions) {
        if (permissions.isSettingsPassed) {
            mainViewModel.getQuotesFromApi()
            showDelay(permissions.isPopupPassed)
        } else {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHelloFragment()
            )
        }
    }

    private fun showDelay(isPopupPassed: Boolean) {
        if (isPopupPassed) {
            openActivity()
        } else {
            lifecycleScope.launch {
                delay(2000L)
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

    private fun setNotificationWorker(notification: Notification) {
        val workTag = "WORK_TAG"

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
            workTag,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}