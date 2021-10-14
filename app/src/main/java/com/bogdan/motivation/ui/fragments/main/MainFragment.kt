package com.bogdan.motivation.ui.fragments.main

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
import com.bogdan.motivation.data.entities.local.Notification
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.databinding.FragmentMainBinding
import com.bogdan.motivation.di.Application
import com.bogdan.motivation.helpers.Constants
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.worker.NotificationsWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Application.appComponent.inject(this)
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

    private fun initializeObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is State.SuccessState<*> -> when (it.data) {
                    is Notification -> setNotificationWorker(it.data)
                    is Utils -> chooseActivityToOpen(it.data)
                }
                is State.ErrorState -> {
                }
            }
        }
    }

    private fun chooseActivityToOpen(utils: Utils) {
        if (utils.isSettingsPassed) {
            if (utils.isPopupPassed) {
                openMotivationFragment()
            } else {
                viewModel.getQuotesFromApi().invokeOnCompletion {
                    openMotivationFragment()
                }
            }
        } else {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToHelloFragment()
            )
        }
    }

    private fun openMotivationFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToMotivationFragment(
                "Общие"
            )
        )
    }

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