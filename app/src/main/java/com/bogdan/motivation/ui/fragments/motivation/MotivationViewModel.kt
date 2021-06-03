package com.bogdan.motivation.ui.fragments.motivation

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MotivationViewModel : BaseViewModel() {

    private val utilsDb = RepositoryProvider.utilsRepository
    private val quotesDb = RepositoryProvider.quotesRepository

    init {
        readPermissions()
    }

    private fun readPermissions() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(utilsDb.getPermissions()))
        }
    }

    fun readAllQuotesFromQuotesDb() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(quotesDb.getAllQuotes()))
        }
    }

    fun readFavouriteQuotesFromQuotesDb() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(quotesDb.getFavoriteQuotes()))
        }
    }

    fun updatePermissions(utils: Utils) {
        viewModelScope.launch(IO) {
            utilsDb.updatePermissions(utils)
        }
    }

    fun updateQuote(quote: String, favorite: Boolean) {
        viewModelScope.launch(IO) {
            quotesDb.updateQuote(quote, favorite)
        }
    }
}