package com.bogdan.motivation.ui.fragments.account

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.data.repositories.UserRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val quotesRepository: QuotesRepository
) : BaseViewModel() {

    init {
        getUserName()
        getUser()
        getFavQuotes()
        getAllComments()
    }

    private fun getUser() = viewModelScope.launch {
        state.value = State.SuccessState(userRepository.getUser())
    }

    private fun getFavQuotes() = viewModelScope.launch {
        state.value = State.SuccessState(quotesRepository.getFavoriteQuotesContent())
    }

    private fun getAllComments() = viewModelScope.launch {
        state.value = State.SuccessState(quotesRepository.getAllComments())
    }

    private fun getUserName() = viewModelScope.launch {
        state.value = State.SuccessState(userRepository.getUserName())
    }
}