package com.bogdan.motivation.ui.fragments.categories

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val utilsRepository: UtilsRepository
) : BaseViewModel() {

    init {
        readFavoriteQuotes()
    }

    private fun readFavoriteQuotes() {
        viewModelScope.launch {
            state.value = State.SuccessState(quotesRepository.getFavoriteQuotes())
        }
    }

    fun updatePermissions(utils: Utils) {
        viewModelScope.launch {
            utilsRepository.updateUtils(utils)
        }
    }
}