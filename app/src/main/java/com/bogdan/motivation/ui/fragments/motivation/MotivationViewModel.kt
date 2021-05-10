package com.bogdan.motivation.ui.fragments.motivation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MotivationViewModel : ViewModel() {

    private val _motivationLiveData: MutableLiveData<MotivationViewState> = MutableLiveData()
    val motivationLiveData: LiveData<MotivationViewState> get() = _motivationLiveData

    private val db = RepositoryProvider.dbRepository

    fun readFavoriteOpenFromPermissionDb() = viewModelScope.launch(IO) {
        val permissionFavoriteOpen = MotivationViewState.FavoriteOpenFromPermissionDb(
            db.readFavoriteOpenFromPermissionDb()
        )
        withContext(Main) {
            _motivationLiveData.postValue(
                permissionFavoriteOpen
            )
        }
    }

    fun readAllQuotesFromQuotesDb() = viewModelScope.launch(IO) {
        val allQuotes = MotivationViewState.AllQuotesFromQuotesDb(
            db.readAllQuotesFromQuotesDb()
        )
        withContext(Main) {
            _motivationLiveData.postValue(
                allQuotes
            )
        }
    }

    fun readFavouriteQuoteFromQuotesDb() = viewModelScope.launch(IO) {
        val favouriteQuote = MotivationViewState.FavouriteQuoteFromQuotesDb(
            db.readFavouriteQuoteFromQuotesDb()
        )
        withContext(Main) {
            _motivationLiveData.postValue(
                favouriteQuote
            )
        }
    }

    fun readPopupFromPermissionsDb() = viewModelScope.launch(IO) {
        val permissionPopup = MotivationViewState.PopupFromPermissionsDb(
            db.readPopupFromPermissionsDb()
        )
        withContext(Main) {
            _motivationLiveData.postValue(
                permissionPopup
            )
        }
    }

    fun insetToPermissionsDb(
        isSettingsPassed: String,
        isPopupPassed: String,
        isFavoriteOpen: String
    ) = viewModelScope.launch(IO) {
        db.insetToPermissionsDb(
            isSettingsPassed,
            isPopupPassed,
            isFavoriteOpen
        )
    }

    fun insertFavoriteKeyToQuotesDb(favorite: String, quote: String) = viewModelScope.launch(IO) {
        db.insertFavoriteKeyToQuotesDb(favorite, quote)
    }
}