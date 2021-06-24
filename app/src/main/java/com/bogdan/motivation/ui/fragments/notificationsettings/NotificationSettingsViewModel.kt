package com.bogdan.motivation.ui.fragments.notificationsettings

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Notification
import com.bogdan.motivation.data.repositories.NotificationsRepository
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationSettingsViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository,
) : BaseViewModel() {

    fun saveNotification(notification: Notification) {
        viewModelScope.launch {
            notificationsRepository.insertNotification(notification)
        }
    }
}