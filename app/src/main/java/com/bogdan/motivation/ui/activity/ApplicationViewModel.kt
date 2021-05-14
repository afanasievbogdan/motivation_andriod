package com.bogdan.motivation.ui.activity

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Permissions
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.ui.BaseViewModel
import com.bogdan.motivation.ui.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationViewModel : BaseViewModel() {

    val db = RepositoryProvider.dbRepository

    fun connectToDb(applicationContext: Context) {
        db.connectToDb(applicationContext)
    }

    fun savePermissions(
        permissions: Permissions
    ) = viewModelScope.launch(IO) {
        db.savePermissions(
            permissions
        )
    }

    fun readCurrentStyle() = viewModelScope.launch(IO) {
        val currentStyle = db.readCurrentStyle()
        withContext(Main) {
            state.value = State.SuccessState(currentStyle)
        }
    }
}