package com.bogdan.motivation.ui.fragments.notificationsettings

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Notification
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NotificationSettingsViewModel : BaseViewModel() {

    private val notificationsDb = RepositoryProvider.notificationsRepository

    fun saveNotification(notification: Notification) {
        viewModelScope.launch(IO) {
            notificationsDb.insertNotification(notification)
        }
    }
}