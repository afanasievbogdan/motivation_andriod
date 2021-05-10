package com.bogdan.motivation.ui.fragments.main

sealed class MainViewState {
    class SettingsFromPermissionsDb(val settings: String) : MainViewState()
    class PopupFromPermissionsDb(val permissionPopup: String) : MainViewState()
    class QuantityFromNotificationsDb(val notificationsQuantity: String) : MainViewState()
    class StartTimeFromNotificationsDb(val notificationsStartTime: String) : MainViewState()
    class EndTimeFromNotificationsDb(val notificationsEndTime: String) : MainViewState()
}