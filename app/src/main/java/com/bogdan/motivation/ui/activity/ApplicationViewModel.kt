package com.bogdan.motivation.ui.activity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import com.bogdan.motivation.helpers.StylesUtils
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationViewModel : ViewModel() {

    private val _applicationLiveData: MutableLiveData<StylesUtils.Styles> = MutableLiveData()
    val applicationLiveData: LiveData<StylesUtils.Styles> get() = _applicationLiveData

    private val db = RepositoryProvider.dbRepository

    fun connectToDb(context: Context) = viewModelScope.launch(IO) {
        db.connectToDb(context)
    }

    fun readStyleFromStylesDb() = viewModelScope.launch(IO) {
        val style = db.readStyleFromStylesDb()
        withContext(Main) {
            _applicationLiveData.postValue(style)
        }
    }
}