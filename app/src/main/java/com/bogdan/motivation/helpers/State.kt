package com.bogdan.motivation.helpers

// TODO: 15.05.2021 удали пустую строку внизу. перенеси в helpers этот класс, добавь ErrorState
sealed class State {
    class SuccessState<T>(val data: T) : State()
    class ErrorState(val errorMessage: String) : State()
}