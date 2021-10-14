package com.bogdan.motivation.ui.fragments.comments

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.repositories.QuotesRepository
import com.bogdan.motivation.data.repositories.UserRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    init {
        getUserName()
    }

    fun getQuote(quote: String) {
        viewModelScope.launch {
            state.value = State.SuccessState(quotesRepository.getQuote(quote))
        }
    }

    fun updateQuoteComments(quote: String, comments: List<String>) =
        viewModelScope.launch {
            quotesRepository.updateQuoteComments(quote, comments)
        }

    private fun getUserName() = viewModelScope.launch {
        state.value = State.SuccessState(userRepository.getUserName())
    }
}
