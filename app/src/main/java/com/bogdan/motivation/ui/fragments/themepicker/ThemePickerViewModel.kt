package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThemePickerViewModel : ViewModel() {

    private val _themePickerLiveData: MutableLiveData<String> = MutableLiveData()
    val themePickerLiveData: LiveData<String> get() = _themePickerLiveData

    private val db = RepositoryProvider.dbRepository

    fun readFromThemesDb() = viewModelScope.launch(IO) {
        val isThemeChosen = db.readFromThemesDb()
        withContext(Main) {
            _themePickerLiveData.setValue(isThemeChosen)
        }
    }

    fun insetToThemesDb(theme: String) = viewModelScope.launch(IO) {
        db.insetToThemesDb(theme)
    }

    fun insetToPermissionsDb(
        isSettingsPassed: String,
        isPopupPassed: String,
        isFavoriteOpen: String
    ) = viewModelScope.launch(IO) {
        db.insetToPermissionsDb(
            isSettingsPassed,
            isPopupPassed,
            isFavoriteOpen
        )
    }
}