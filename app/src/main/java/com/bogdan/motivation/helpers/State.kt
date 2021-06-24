package com.bogdan.motivation.helpers

sealed class State {
    class SuccessState<T>(val data: T) : State()
    class ErrorState(val errorMessage: String) : State()
}