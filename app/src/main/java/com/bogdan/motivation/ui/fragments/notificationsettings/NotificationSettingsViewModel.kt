package com.bogdan.motivation.ui.fragments.notificationsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NotificationSettingsViewModel : ViewModel() {

    private val db = RepositoryProvider.dbRepository

    fun insetToNotificationsDb(quantity: String, startTime: String, endTime: String) =
        viewModelScope.launch(IO) {
            db.insetToNotificationsDb(quantity, startTime, endTime)
        }
}