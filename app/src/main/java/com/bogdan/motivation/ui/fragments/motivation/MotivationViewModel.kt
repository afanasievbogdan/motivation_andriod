package com.bogdan.motivation.ui.fragments.motivation

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MotivationViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val quotesRepository: QuotesRepository
) : BaseViewModel() {

    fun readUtils() {
        viewModelScope.launch {
            state.value = State.SuccessState(utilsRepository.getUtils())
        }
    }

    fun readAllQuotesFromQuotesDb() {
        viewModelScope.launch {
            state.value = State.SuccessState(quotesRepository.getAllQuotes())
        }
    }

    fun readFavouriteQuotesFromQuotesDb() {
        viewModelScope.launch {
            state.value = State.SuccessState(quotesRepository.getFavoriteQuotes())
        }
    }

    fun updateUtils(utils: Utils) {
        viewModelScope.launch {
            utilsRepository.updateUtils(utils)
        }
    }

    fun updateQuote(quote: String, favorite: Boolean) {
        viewModelScope.launch {
            quotesRepository.updateQuote(quote, favorite)
        }
    }
}