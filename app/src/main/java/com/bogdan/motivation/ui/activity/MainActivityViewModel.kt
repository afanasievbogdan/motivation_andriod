package com.bogdan.motivation.ui.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.api.QuotesApi
import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.*
import com.bogdan.motivation.helpers.State
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application, private val quotesApi: QuotesApi) : AndroidViewModel(application) {
    // TODO: 15.05.2021 почему не private

    private val utilsDb: UtilsRepository
    private val stylesDb: StylesRepository

    val state: MutableLiveData<State> = MutableLiveData<State>()

    init {
        with(RepositoryProvider) {
            quotesRepository.db = ApplicationDatabase.getDB(application)
            notificationsRepository.db = ApplicationDatabase.getDB(application)
            utilsRepository.db = ApplicationDatabase.getDB(application)
            stylesRepository.db = ApplicationDatabase.getDB(application)
        }

        utilsDb = RepositoryProvider.utilsRepository
        stylesDb = RepositoryProvider.stylesRepository

        readCurrentStyle()

        saveUtils(
            Utils(
                isSettingsPassed = false,
                isPopupPassed = false,
                isFavoriteTabOpen = false
            )
        )
    }

    // TODO: 15.05.2021 убери ёлку
    private fun saveUtils(utils: Utils) {
        viewModelScope.launch {
            utilsDb.insertUtils(utils)
        }
    }

    // TODO: 15.05.2021 в таких случаях лучше {}, а не =
    private fun readCurrentStyle() {
        viewModelScope.launch {
            state.value = State.SuccessState(stylesDb.getStyle())
        }
    }
}