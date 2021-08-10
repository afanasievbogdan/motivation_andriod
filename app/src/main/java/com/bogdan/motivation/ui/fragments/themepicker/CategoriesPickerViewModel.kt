package com.bogdan.motivation.ui.fragments.themepicker

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.Categories
import com.bogdan.motivation.data.entities.local.Utils
import com.bogdan.motivation.data.repositories.CategoriesListRepository
import com.bogdan.motivation.data.repositories.CategoriesRepository
import com.bogdan.motivation.data.repositories.UtilsRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesPickerViewModel @Inject constructor(
    private val utilsRepository: UtilsRepository,
    private val categoriesListRepository: CategoriesListRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel() {

    init {
        getThemeList()
    }

    private fun getThemeList() {
        state.value = State.SuccessState(categoriesListRepository.getCategoriesList())
    }

    fun updateUtils(utils: Utils) {
        viewModelScope.launch { utilsRepository.updateUtils(utils) }
    }

    fun insertTheme(categories: Categories) {
        viewModelScope.launch { categoriesRepository.insertCategory(categories) }
    }
}