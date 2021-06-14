package com.bogdan.motivation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val api = RepositoryProvider.quotesApiRepository
    private val utilsDb = RepositoryProvider.utilsRepository
    private val notificationsDb = RepositoryProvider.notificationsRepository

    init {
        readUtils()
        readNotification()
    }

    private fun readUtils() {
        viewModelScope.launch {
            val utils = utilsDb.getUtils()
            state.value = State.SuccessState(utils)
        }
    }

    private fun readNotification() {
        viewModelScope.launch {
            state.value = State.SuccessState(notificationsDb.getNotification())
        }
    }

    fun getQuotesFromApi() = viewModelScope.launch {
        api.getQuotesFromApi()
    }
}