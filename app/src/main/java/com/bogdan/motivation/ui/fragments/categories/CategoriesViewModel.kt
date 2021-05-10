package com.bogdan.motivation.ui.fragments.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.Quote
import com.bogdan.motivation.data.repositories.RepositoryProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel : ViewModel() {

    private val _categoriesLiveData: MutableLiveData<ArrayList<Quote>> = MutableLiveData()
    val categoriesLiveData: LiveData<ArrayList<Quote>> get() = _categoriesLiveData

    private val db = RepositoryProvider.dbRepository

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

    fun readFavouriteQuoteFromQuotesDb() = viewModelScope.launch(IO) {
        val favouriteQuote = db.readFavouriteQuoteFromQuotesDb()
        withContext(Main) {
            _categoriesLiveData.postValue(favouriteQuote)
        }
    }
}