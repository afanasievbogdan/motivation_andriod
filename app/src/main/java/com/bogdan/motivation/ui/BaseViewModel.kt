package com.bogdan.motivation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData<State>()
}