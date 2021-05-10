package com.bogdan.motivation.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _mainLiveData: MutableLiveData<MainViewState> = MutableLiveData<MainViewState>()
    val mainLiveData: LiveData<MainViewState> get() = _mainLiveData

    private val db = RepositoryProvider.dbRepository

    fun readSettingsFromPermissionsDb() = viewModelScope.launch(IO) {
        val permissionSettings = MainViewState.SettingsFromPermissionsDb(
            db.readSettingsFromPermissionsDb()
        )
        withContext(Main) {
            _mainLiveData.postValue(
                permissionSettings
            )
        }
    }

    fun readPopupFromPermissionsDb() = viewModelScope.launch(IO) {
        val permissionPopup = MainViewState.PopupFromPermissionsDb(
            db.readPopupFromPermissionsDb()
        )
        withContext(Main) {
            _mainLiveData.postValue(
                permissionPopup
            )
        }
    }

    fun readQuantityFromNotificationsDb() = viewModelScope.launch(IO) {
        val notificationsQuantity = MainViewState.QuantityFromNotificationsDb(
            db.readQuantityFromNotificationsDb()
        )
        withContext(Main) {
            _mainLiveData.postValue(
                notificationsQuantity
            )
        }
    }

    fun readStartTimeFromNotificationsDb() = viewModelScope.launch(IO) {
        val startTime = MainViewState.StartTimeFromNotificationsDb(
            db.readStartTimeFromNotificationsDb()
        )
        withContext(Main) {
            _mainLiveData.postValue(
                startTime
            )
        }
    }

    fun readEndTimeFromNotificationsDb() = viewModelScope.launch(IO) {
        val endTime = MainViewState.EndTimeFromNotificationsDb(
            db.readEndTimeFromNotificationsDb()
        )
        withContext(Main) {
            _mainLiveData.postValue(
                endTime
            )
        }
    }

    fun getQuotesFromApi() {
        viewModelScope.launch(IO) {
            RepositoryProvider.quotesRepository.getQuotesFromApi(
                db.getDbInstance()
            )
        }
    }
}