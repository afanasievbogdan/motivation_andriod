package com.bogdan.motivation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.*
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val notificationsRepository: NotificationsRepository,
    private val quotesApiRepository: QuotesApiRepository
) : BaseViewModel() {

    init {
        readUtils()
        readNotification()
    }

    private fun readUtils() {
        viewModelScope.launch {
            val utils = utilsRepository.getUtils()
            state.value = State.SuccessState(utils)
        }
    }

    private fun readNotification() {
        viewModelScope.launch {
            state.value = State.SuccessState(notificationsRepository.getNotification())
        }
    }

    fun getQuotesFromApi() = viewModelScope.launch {
        quotesApiRepository.getQuotesFromApi()
    }
}