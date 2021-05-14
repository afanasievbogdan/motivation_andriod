package com.bogdan.motivation.ui

sealed class State {
    class SuccessState<T>(val data: T) : State()
}
