package com.bogdan.motivation.ui.fragments.categories

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch

class CategoriesViewModel : BaseViewModel() {
    // TODO: 15.05.2021 Вынеси в бейз, зачем в каждом инициализировать переменную

    private val quotesDb = RepositoryProvider.quotesRepository
    private val utilsDb = RepositoryProvider.utilsRepository

    init {
        readFavoriteQuotes()
    }

    // TODO: 15.05.2021 postvalue без withcontext(main) не работает?
    private fun readFavoriteQuotes() {
        viewModelScope.launch {
            state.value = State.SuccessState(quotesDb.getFavoriteQuotes())
        }
    }

    fun updatePermissions(utils: Utils) {
        viewModelScope.launch {
            utilsDb.updateUtils(utils)
        }
    }
}