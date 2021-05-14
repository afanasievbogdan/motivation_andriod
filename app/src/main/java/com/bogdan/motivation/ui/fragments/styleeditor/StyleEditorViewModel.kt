package com.bogdan.motivation.ui.fragments.styleeditor

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.CurrentStyle
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class StyleEditorViewModel : BaseViewModel() {

    val db = RepositoryProvider.dbRepository

    fun saveCurrentStyle(currentStyle: CurrentStyle) = viewModelScope.launch(IO) {
        db.saveCurrentStyle(currentStyle)
    }
}