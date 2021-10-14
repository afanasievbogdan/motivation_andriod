package com.bogdan.motivation.ui.fragments.you

import androidx.lifecycle.viewModelScope
import com.bogdan.motivation.data.entities.local.User
import com.bogdan.motivation.data.repositories.UserRepository
import com.bogdan.motivation.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class YouViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun insertUser(user: User) = viewModelScope.launch {
        userRepository.insertUser(user)
    }
}