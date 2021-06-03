package com.bogdan.motivation.ui.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.api.RetrofitConfiguration
import com.bogdan.motivation.data.db.ApplicationDatabase
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.*
import com.bogdan.motivation.helpers.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: 15.05.2021 почему не private

    private val utilsDb: UtilsRepository
    private val stylesDb: StylesRepository

    val state: MutableLiveData<State> = MutableLiveData<State>()

    init {
        RetrofitConfiguration.createQuotesApiInstance()
        RepositoryProvider.quotesRepository.db = ApplicationDatabase.getDB(application)
        RepositoryProvider.notificationsRepository.db = ApplicationDatabase.getDB(application)
        RepositoryProvider.utilsRepository.db = ApplicationDatabase.getDB(application)
        RepositoryProvider.stylesRepository.db = ApplicationDatabase.getDB(application)

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
        viewModelScope.launch(IO) {
            utilsDb.insertPermissions(utils)
        }
    }

    // TODO: 15.05.2021 в таких случаях лучше {}, а не =
    private fun readCurrentStyle() {
        viewModelScope.launch(IO) {
            state.postValue(State.SuccessState(stylesDb.getStyle()))
        }
    }
}