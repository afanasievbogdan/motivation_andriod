package com.bogdan.motivation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val api = RepositoryProvider.quotesApiRepository
    private val utilsDb = RepositoryProvider.utilsRepository
    private val notificationsDb = RepositoryProvider.notificationsRepository

    init {
        readPermissions()
        readNotification()
    }

    private fun readPermissions() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(utilsDb.getPermissions()))
        }
    }

    private fun readNotification() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(notificationsDb.getNotification()))
        }
    }

    fun getQuotesFromApi() = viewModelScope.launch(IO) {
        api.getQuotesFromApi()
    }
}