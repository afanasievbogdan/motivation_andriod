package com.bogdan.motivation.ui.fragments.categories

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import com.bogdan.motivation.ui.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel : BaseViewModel() {
    // TODO: 15.05.2021 Вынеси в бейз, зачем в каждом инициализировать переменную
    private val db = RepositoryProvider.dbRepository

    init {
        readFavoriteQuotes()
    }

    // TODO: 15.05.2021 postvalue без withcontext(main) не работает?
    private fun readFavoriteQuotes() = viewModelScope.launch(IO) {
        val favouriteQuotes = db.readFavoriteQuotes()
        withContext(Main) {
            state.value = State.SuccessState(favouriteQuotes)
        }
    }

    fun updatePermissions(
        permissions: Permissions
    ) = viewModelScope.launch(IO) {
        db.updatePermissions(
            permissions
        )
    }
}