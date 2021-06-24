package com.bogdan.motivation.ui.fragments.styleeditor

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.data.repositories.StylesRepository
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class StyleEditorViewModel @Inject constructor(
    private val stylesRepository: StylesRepository,
) : BaseViewModel() {

    fun saveCurrentStyle(style: Style) {
        viewModelScope.launch {
            stylesRepository.insertStyle(style)
        }
    }
}