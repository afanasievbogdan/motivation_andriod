package com.bogdan.motivation.ui.activity

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.StylesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val stylesRepository: StylesRepository
) : BaseViewModel() {

    init {
        readCurrentStyle()

        saveUtils(
            Utils(
                isSettingsPassed = false,
                isPopupPassed = false,
                isFavoriteTabOpen = false
            )
        )
    }

    private fun saveUtils(utils: Utils) {
        viewModelScope.launch {
            utilsRepository.insertUtils(utils)
        }
    }

    private fun readCurrentStyle() {
        viewModelScope.launch {
            state.value = State.SuccessState(stylesRepository.getStyle())
        }
    }
}