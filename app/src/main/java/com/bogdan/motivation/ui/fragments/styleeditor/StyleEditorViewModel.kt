package com.bogdan.motivation.ui.fragments.styleeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.repositories.RepositoryProvider
import kotlinx.coroutines.launch

class StyleEditorViewModel : ViewModel() {

    private val db = RepositoryProvider.dbRepository.getDbInstance()

    fun insertStyleToStylesDb(style: String) {
        viewModelScope.launch {
            db.insertStyleToStylesDb(style)
        }
    }
}