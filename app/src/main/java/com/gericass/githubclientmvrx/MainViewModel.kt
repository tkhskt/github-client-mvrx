package com.gericass.githubclientmvrx

import com.airbnb.mvrx.MvRxState
import com.gericass.githubclientmvrx.common.core.MvRxViewModel

data class MainState(
    val isEditingKeyword: Boolean = false
) : MvRxState

class MainViewModel(initialState: MainState) : MvRxViewModel<MainState>(initialState) {


}