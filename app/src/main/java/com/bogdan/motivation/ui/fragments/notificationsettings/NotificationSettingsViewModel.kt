package com.bogdan.motivation.ui.fragments.notificationsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.repositories.RepositoryProvider
import kotlinx.coroutines.launch

class NotificationSettingsViewModel : ViewModel() {

    private val db = RepositoryProvider.dbRepository.getDbInstance()

    fun insetToNotificationsDb(quantity: String, startTime: String, endTime: String) {
        viewModelScope.launch {
            db.insetToNotificationsDb(quantity, startTime, endTime)
        }
    }
}