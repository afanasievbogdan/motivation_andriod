package com.bogdan.motivation.ui.fragments.notificationsettings

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Notification
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NotificationSettingsViewModel : BaseViewModel() {

    val db = RepositoryProvider.dbRepository

    fun saveNotification(notification: Notification) = viewModelScope.launch(IO) {
        db.saveNotification(notification)
    }
}