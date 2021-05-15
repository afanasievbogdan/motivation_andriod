package com.bogdan.motivation.ui

// TODO: 15.05.2021 удали пустую строку внизу. перенеси в helpers этот класс, добавь ErrorState
sealed class State {
    class SuccessState<T>(val data: T) : State()
}
