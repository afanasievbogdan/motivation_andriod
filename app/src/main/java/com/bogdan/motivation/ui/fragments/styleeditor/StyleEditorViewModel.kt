package com.bogdan.motivation.ui.fragments.styleeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StyleEditorViewModel : ViewModel() {

    private val db = RepositoryProvider.dbRepository

    fun insertStyleToStylesDb(style: String) = viewModelScope.launch(IO) {
        db.insertStyleToStylesDb(style)
    }
}