package com.gericass.githubclientmvrx

import com.airbnb.mvrx.MvRxState
import com.gericass.githubclientmvrx.core.BaseViewModel

data class MainState(
        val isEditingKeyword: Boolean = false
) : MvRxState

class MainViewModel(initialState: MainState) : BaseViewModel<MainState>(initialState) {


}