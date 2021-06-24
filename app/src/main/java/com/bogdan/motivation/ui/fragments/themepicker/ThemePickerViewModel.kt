package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.ThemesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemePickerViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val themesRepository: ThemesRepository
) : BaseViewModel() {

    init {
        getThemeList()
    }

    private fun getThemeList() {
        state.value = State.SuccessState(themesRepository.getThemeList())
    }

    fun updatePermissions(utils: Utils) {
        viewModelScope.launch {
            utilsRepository.updateUtils(utils)
        }
    }
}