package com.bogdan.motivation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogdan.motivation.helpers.State

abstract class BaseViewModel : ViewModel() {

    val state = MutableLiveData<State>()
}