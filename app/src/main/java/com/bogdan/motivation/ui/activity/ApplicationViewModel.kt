package com.bogdan.motivation.ui.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.db.DBManager
import com.bogdan.motivation.helpers.StylesUtils
import com.bogdan.motivation.repositories.RepositoryProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ApplicationViewModel : ViewModel() {

    lateinit var db: DBManager

    fun connectToDb(context: Context) {
        viewModelScope.launch {
            RepositoryProvider.dbRepository.connectToDb(context)
        }
    }

    private fun getDbInstance() {
        db = RepositoryProvider.dbRepository.getDbInstance()
    }

    private fun styleFromStylesDb(): StylesUtils.Styles {
        getDbInstance()
        return db.readStyleFromStylesDb()
    }

    fun readStyleFromStylesDb(): StylesUtils.Styles = runBlocking {
        styleFromStylesDb()
    }
}