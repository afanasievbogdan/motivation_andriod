package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ThemePickerViewModel : BaseViewModel() {

    val db = RepositoryProvider.dbRepository

    fun updatePermissions(
        permissions: Permissions
    ) = viewModelScope.launch(IO) {
        db.updatePermissions(
            permissions
        )
    }
}