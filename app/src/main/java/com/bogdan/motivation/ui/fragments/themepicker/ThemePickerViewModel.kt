package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch

class ThemePickerViewModel : BaseViewModel() {

    private val utilsDb = RepositoryProvider.utilsRepository

    init {
        getThemeList()
    }

    private fun getThemeList() {
        state.value = State.SuccessState(RepositoryProvider.themesRepository.getThemeList())
    }

    fun updatePermissions(utils: Utils) {
        viewModelScope.launch {
            utilsDb.updateUtils(utils)
        }
    }
}