package com.bogdan.motivation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import com.bogdan.motivation.ui.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : BaseViewModel() {

    private val db = RepositoryProvider.dbRepository
    private val api = RepositoryProvider.quotesRepository

    init {
        readPermissions()
        readNotification()
    }

    private fun readPermissions() = viewModelScope.launch(IO) {
        val permission = db.readPermissions()
        withContext(Main) {
            state.value = State.SuccessState(permission)
        }
    }

    private fun readNotification() = viewModelScope.launch(IO) {
        val notification = db.readNotification()
        withContext(Main) {
            state.value = State.SuccessState(notification)
        }
    }

    fun getQuotesFromApi() = viewModelScope.launch(IO) {
        api.getQuotesFromApi()
    }
}