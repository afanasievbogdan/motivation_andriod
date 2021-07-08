package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Themes
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.ThemesListRepository
import com.bogdan.motivation.data.repositories.ThemesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemePickerViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val themesListRepository: ThemesListRepository,
    private val themesRepository: ThemesRepository
) : BaseViewModel() {

    init {
        getThemeList()
    }

    private fun getThemeList() {
        state.value = State.SuccessState(themesListRepository.getThemeList())
    }

    fun updateUtils(utils: Utils) {
        viewModelScope.launch { utilsRepository.updateUtils(utils) }
    }

    fun insertTheme(themes: Themes) {
        viewModelScope.launch { themesRepository.insertTheme(themes) }
    }
}