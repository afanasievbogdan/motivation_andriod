package com.bogdan.motivation.ui.fragments.aboutme

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.data.repositories.UserRepository
import com.bogdan.motivation.helpers.State
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AboutMeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    init {
        getUser()
    }

    fun getUser() = viewModelScope.launch {
        state.value = State.SuccessState(userRepository.getUser())
    }

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}