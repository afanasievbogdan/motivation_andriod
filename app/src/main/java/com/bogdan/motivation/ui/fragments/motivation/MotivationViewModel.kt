package com.bogdan.motivation.ui.fragments.motivation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import com.bogdan.motivation.ui.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MotivationViewModel : BaseViewModel() {

    private val db = RepositoryProvider.dbRepository

    // TODO: 15.05.2021 зачем это если есть state
    val allQuotesLiveData: MutableLiveData<List<Quote>> = MutableLiveData()
    val favouriteQuotesLiveData: MutableLiveData<List<Quote>> = MutableLiveData()

    init {
        readPermissions()
        readAllQuotesFromQuotesDb()
        readFavouriteQuotesFromQuotesDb()
    }

    private fun readPermissions() = viewModelScope.launch(IO) {
        val permission = db.readPermissions()
        withContext(Main) {
            state.value = State.SuccessState(permission)
        }
    }

    private fun readAllQuotesFromQuotesDb() = viewModelScope.launch(IO) {
        val allQuotes = db.readAllQuotes()
        withContext(Main) {
            allQuotesLiveData.value = allQuotes
        }
    }

    private fun readFavouriteQuotesFromQuotesDb() = viewModelScope.launch(IO) {
        val favoriteQuotes = db.readFavoriteQuotes()
        withContext(Main) {
            favouriteQuotesLiveData.value = favoriteQuotes
        }
    }

    fun updatePermissions(permissions: Permissions) = viewModelScope.launch(IO) {
        db.updatePermissions(
            permissions
        )
    }

    fun updateQuote(quote: String, favorite: Boolean) = viewModelScope.launch(IO) {
        db.updateQuote(quote, favorite)
    }
}