package com.bogdan.motivation.ui.fragments.styleeditor

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Style
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StyleEditorViewModel : BaseViewModel() {

    private val stylesDb = RepositoryProvider.stylesRepository

    fun saveCurrentStyle(style: Style) {
        viewModelScope.launch(IO) {
            stylesDb.insertStyle(style)
        }
    }
}